package ru.pf.metadata.reader;

import ru.pf.metadata.Module;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public class ModuleReader {

    public static Module read(Path bslFile, Module.Type type) throws IOException {

        Module instance = new Module(bslFile, type);

        StringBuilder sb = new StringBuilder();
        Files.lines(bslFile, StandardCharsets.UTF_8).forEach(s -> {
            sb.append(s).append(System.getProperty("line.separator"));

            String doProcess = s.trim();
            if (doProcess.toLowerCase().startsWith("процедура")
                    || doProcess.toLowerCase().startsWith("функция")) {
                instance.getMethods().add(MethodReader.read(doProcess));
            }
        });

        instance.setText(sb.toString());

        return instance;
    }
}
