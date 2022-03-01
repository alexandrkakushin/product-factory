package ru.pf.yellow;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Экземпляр платформы 1С:Предприятие
 * @author a.kakushin
 */
@Getter
public class Yellow {

    /**
     * Исполняемый файл толстого клиента
     */
    private Path thickClient;

    /**
     * Конструктор с указанием исполняемого файла
     * @param thickClient Путь к исполняемому файлу
     */
    public Yellow(Path thickClient) throws YellowException {
        if (!Files.exists(thickClient)) {
            throw new YellowException("Platform not found: " + thickClient);
        }
        this.thickClient = thickClient;
    }
}
