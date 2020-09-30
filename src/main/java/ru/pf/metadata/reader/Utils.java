package ru.pf.metadata.reader;

import ru.pf.metadata.object.common.HttpService;
import ru.pf.metadata.object.common.XdtoPackage;

/**
 * Вспомогательгый класс для парсинга конфигурации
 * @author a.kakushin
 */
public class Utils {

    private Utils() {
        throw  new IllegalStateException("Utility class");
    }

    /**
     * Получение имени типа данных по классу объекта метаданных
     * @param clazz Класс объекта метаданных
     * @return Имя типа данных
     */
    public static String getRefType(Class<?> clazz) {
        // При разборе XML-файла конфигурации имена узлов не соответсвуют
        // именам Java-классов

        String nodeName;
        if (clazz.equals(XdtoPackage.class)) {
            nodeName = "XDTOPackage";
        } else if (clazz.equals(HttpService.class)) {
            nodeName = "HTTPService";
        } else {
            nodeName = clazz.getSimpleName();
        }

        return nodeName;
    }
}
