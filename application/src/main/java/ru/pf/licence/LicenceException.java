package ru.pf.licence;

/**
 * Исключение при работе с утилитой лицензирования СЛК
 * @author a.kakushin
 */
public class LicenceException extends Exception {

    /**
     * Конструктор с указанием текста исключения
     * @param message Текст исключения
     */
    public LicenceException(String message) {
        super(message);
    }

    /**
     * Конструктор с указанием класса исключения
     * @param cause Исключение
     */
    public LicenceException(Throwable cause) {
        super(cause);
    }
}
