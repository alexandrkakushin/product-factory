package ru.pf.controller.crud;

/**
 * Класс-исключение для для ошибок записи сущностей в БД
 * @author a.kakushin
 */
public class SubmitException extends Exception {

    /**
     * Конструктор исключения с указанием текста
     * @param message Текст исключения
     */
    public SubmitException(String message) {
        super(message);
    }

    /**
     * Конструктор с передачей класса исключения
     * @param cause Исключение
     */
    public SubmitException(Throwable cause) {
        super(cause);
    }
}
