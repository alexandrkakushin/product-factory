package ru.pf.metadata.reader;

import ru.pf.metadata.Module;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a.kakushin
 */
public class ModuleReader {

    public static Module read(Path bslFile, Module.Type type) throws IOException {
        if (!Files.exists(bslFile)) {
            return null;
        }

        Module instance = new Module(bslFile, type);

        StringBuilder sb = new StringBuilder();
        List<Integer> lines = new ArrayList<>();

        Files.lines(bslFile, StandardCharsets.UTF_8).forEach(s -> {
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
}
