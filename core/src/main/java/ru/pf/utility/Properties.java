package ru.pf.utility;

import java.util.HashSet;
import java.util.Set;

/**
 * Утилитный класс для перечисления доступных для настройки свойств
 * @author a.kakushin
 */
public class Properties {

    /**
     * Каталог хранения конфигураций
     */
    public static final String STORAGE = "STORAGE";

    /**
     * Максимальная длина имен объектов метаданных
     */
    public static final String CHECK_NAME_LENGTH = "CHECK_NAME_LENGTH";

    /**
     * Максимальная длина строки кода
     */
    public static final String CHECK_LINE_SIZE = "CHECK_LINE_SIZE";

    /**
     * Каталог установленных платформ 1С:Предприятие
     */
    public static final String DIR_VERSIONS_1C = "DIR_VERSIONS_1C";

    /**
     * Скрытый конструктор по умолчанию
     */
    private Properties() {
        throw  new IllegalStateException("Utility class");
    }

    /**
     * Список доступных для настройки свойств
     * @return Набор свойств
     */
    public static Set<String> available() {
        Set<String> result = new HashSet<>();
        result.add(STORAGE);
        result.add(CHECK_NAME_LENGTH);
        result.add(CHECK_LINE_SIZE);
        result.add(DIR_VERSIONS_1C);

        return result;
    }
}