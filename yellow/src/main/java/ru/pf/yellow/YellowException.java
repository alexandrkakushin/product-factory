package ru.pf.yellow;

/**
 * Исключение пакетного режима 1С:Предприятие
 * @author a.kakushin
 */
public class YellowException extends Exception {

    /**
     * Конструктор с указанием текста исключения
     * @param message Текст исключения
     */
    public YellowException(String message) {
        super(message);
    }

    /**
     * Конструктор с указанием исключения
     * @param cause Исключение
     */
    public YellowException(Throwable cause) {
        super(cause);
    }
}
