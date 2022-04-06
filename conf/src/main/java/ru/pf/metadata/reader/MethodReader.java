package ru.pf.metadata.reader;

import ru.pf.metadata.Method;

/**
 * @author a.kakushin
 */
public class MethodReader {

    public static Method read(String text) {
        Method method = new Method(text);

        String oneLine = text.trim();
        if (oneLine.toLowerCase().startsWith("процедура")) {
            method.setType(Method.Type.PROCEDURE);
            method.setName(oneLine.substring(10, oneLine.indexOf("(")));

        } else if (oneLine.toLowerCase().startsWith("функция")) {
            method.setType(Method.Type.FUNCTION);
            method.setName(oneLine.substring(8, oneLine.indexOf("(")));
        }

        // TODO: ключевые слова могут быть в тексте
        if (oneLine.toLowerCase().contains("экспорт")) {
            method.setExport(true);
        }

        fillArgs(method, oneLine);

        return method;
    }

    private static void fillArgs(Method method, String line) {
        String[] parts = line.substring(line.indexOf("(") + 1, line.indexOf(")"))
                .split(",");

        for (String part: parts) {
            Method.Arg arg = new Method.Arg();
            if (part.contains("=")) {
                arg.setName(part.substring(0, part.indexOf("=")).trim());
                arg.setDefaultValue(part.substring(part.indexOf("=") + 1, part.length()).trim());
            } else {
                arg.setName(part);
            }
            method.getArgs().add(arg);
        }
    }
}
