package ru.pf.metadata.reader;

import ru.pf.metadata.Method;

/**
 * @author a.kakushin
 */
public class MethodReader {

    public static Method read(String line) {
        Method method = new Method();

        if (line.toLowerCase().startsWith("процедура")) {
            method.setType(Method.Type.PROCEDURE);
            method.setName(line.substring(10, line.indexOf("(")));

        } else if (line.toLowerCase().startsWith("функция")) {
            method.setType(Method.Type.FUNCTION);
            method.setName(line.substring(8, line.indexOf("(")));
        }

        method.setExport(line.toLowerCase().endsWith("экспорт"));

        fillArgs(method, line);

        return method;
    }

    private static void fillArgs(Method method, String line) {
        String[] parts = line
                .substring(line.indexOf("(") + 1, line.lastIndexOf(")"))
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
