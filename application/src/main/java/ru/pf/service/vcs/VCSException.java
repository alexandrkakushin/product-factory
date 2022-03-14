package ru.pf.service.vcs;

/**
 * Исключение при работе с VCS
 * @author a.kakushin
 */
public class VCSException extends Exception {

    /**
     * Конструктор с указанием текста исключения
     * @param message
     */
    public VCSException(String message) {
        super(message);
    }

    /**
     * Конструктор с указанием класса исключения
     * @param cause
     */
    public VCSException(Throwable cause) {
        super(cause);
    }
}
