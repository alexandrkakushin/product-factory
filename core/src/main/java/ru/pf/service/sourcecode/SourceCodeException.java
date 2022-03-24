package ru.pf.service.sourcecode;

/**
 * Исключение при работе с VCS
 * @author a.kakushin
 */
public class SourceCodeException extends Exception {

    /**
     * Конструктор с указанием текста исключения
     * @param message
     */
    public SourceCodeException(String message) {
        super(message);
    }

    /**
     * Конструктор с указанием класса исключения
     * @param cause
     */
    public SourceCodeException(Throwable cause) {
        super(cause);
    }
}
