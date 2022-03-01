package ru.pf.metadata.reader;

import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Класс-читатель, позволяющий получить содержимое модуля (процедуры, функции)
 * @author a.kakushin
 */
public class ModuleReader {

    private ModuleReader() {
        throw  new IllegalStateException("Utility reader class");
    }

    public static Module read(Path pathExt, Class<?> annotationClass) throws ReaderException {

        Map<Class<?>, TypeFileName> classTypeMap = new HashMap<>();

        classTypeMap.put(PlainModule.class,
            new TypeFileName(Module.Type.PLAIN_MODULE, "Module.bsl"));

        classTypeMap.put(ManagerModule.class,
            new TypeFileName(Module.Type.MANAGER_MODULE, "ManagerModule.bsl"));

        classTypeMap.put(ObjectModule.class,
            new TypeFileName(Module.Type.OBJECT_MODULE, "ObjectModule.bsl"));

        classTypeMap.put(RecordSetModule.class,
            new TypeFileName(Module.Type.RECORD_SET_MODULE, "RecordSetModule.bsl"));

        classTypeMap.put(ValueManagerModule.class,
            new TypeFileName(Module.Type.VALUE_MANAGER_MODULE, "ValueManagerModule.bsl"));

        classTypeMap.put(CommandModule.class,
            new TypeFileName(Module.Type.COMMAND_MODULE, "CommandModule.bsl"));

        TypeFileName target = classTypeMap.get(annotationClass);
        if (target == null) {
            throw new ReaderException("Could not get type and file name");
        }

        return read(pathExt.resolve(target.getFileName()), target.getType());
    }

    /**
     * Чтение содержимого модуля
     * @param bslFile BSL-файл модуль
     * @param type Тип модуля (Модуль объекта, Модуль менеджера, Модуль набора записей и т.д.)
     * @return Экземпляр класс Module
     * @throws ReaderException Обобщенное исключение парсинга
     */
    public static Module read(Path bslFile, Module.Type type) throws ReaderException {
        if (!Files.exists(bslFile)) {
            return null;
        }

        Module instance = new Module(bslFile, type);

        StringBuilder sb = new StringBuilder();
        List<Integer> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(bslFile, StandardCharsets.UTF_8)) {
            stream.forEach(s -> {
                String doProcess = s.trim();

                if (doProcess.toLowerCase().startsWith("процедура")
                        || doProcess.toLowerCase().startsWith("функция")) {
                    lines.add(sb.length() - 1);
                }

                sb.append(s).append(System.getProperty("line.separator"));

                if (doProcess.toLowerCase().startsWith("конецпроцедуры")
                        || doProcess.toLowerCase().startsWith("конецфункции")) {
                    lines.add(sb.length() - 1);
                }
            });
        } catch (IOException ex) {
            throw new ReaderException(ex.getMessage());
        }

        if (lines.size() % 2 != 0) {
            instance.setErrors(true);
            return instance;
        }

        instance.setText(sb.toString());
        for (int i = 0; i < lines.size(); i = i + 2) {
            instance.getMethods()
                    .add(MethodReader.read(sb.substring(lines.get(i), lines.get(i + 1))));
        }

        return instance;
    }

    /**
     * Описание связки Имя файла модуля - Тип модуля
     */
    private static class TypeFileName {
        private final Module.Type type;
        private final String fileName;

        public TypeFileName(Module.Type type, String name) {
            this.type = type;
            this.fileName = name;
        }

        public Module.Type getType() {
            return type;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
