package ru.pf.metadata.reader;

/**
 * Класс-исключение для ошибок "разбора" объекта метаданных
 * @author a.kakushin
 */
public class ReaderException extends Exception {

    public ReaderException(String message) {
        super(message);
    }
}
