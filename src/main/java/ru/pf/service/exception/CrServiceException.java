package ru.pf.service.exception;

/**
 * Исключение при работе с хранилищем конфигурации
 * @author a.kakushin
 */
public class CrServiceException extends Exception {

    /**
     * Конструктор с указанием текста исключения
     * @param message
     */
    public CrServiceException(String message) {
        super(message);
    }
}
