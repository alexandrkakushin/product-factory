package ru.pf.metadata.reader;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.*;
import ru.pf.metadata.object.Enum;
import ru.pf.metadata.object.common.CommonModule;
import ru.pf.metadata.object.common.Language;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author a.kakushin
 */
@Service
public class ConfReader {

    public Conf read(Path workPath) throws IOException {

        Conf conf = null;

        Path fileConfiguration = workPath.resolve("Configuration.xml");
        if (Files.exists(fileConfiguration)) {
            conf = new Conf(fileConfiguration);
        } else {
            throw new FileNotFoundException("File \"Configuration.xml\" not found'");
        }

        /*Общие
             Подсистемы*/

        // Общие модули
        Path workObjects = workPath.resolve("CommonModules");
        if (Files.exists(workObjects)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(workObjects, "*.xml")) {
                for (Path entry : entries) {
                    CommonModule commonModule = new CommonModule(entry);
                    commonModule.parse();
                    conf.getCommonModules().add(commonModule);
                }
            }
        }

             /*Параметры сеанса
             Роли
             Общие реквизиты
             Планы обмена
             Критерии отбора
             Подписки на события
             Регламентные задания
             Функциональные опции
             Параметры функциональных опций
             Определяемые типы
             Хранилища настроек
             Общие формы
             Общие команды
             Группы команд
             Общие макеты
             Общие картинки
             XDTO-пакеты
             Web-сервисы
             HTTP-сервисы
             WS-ссылки
             Элементы стиля*/
        // Языки
        workObjects = workPath.resolve("Languages");
        if (Files.exists(workObjects)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(workObjects, "*.xml")) {
                for (Path entry : entries) {
                    conf.getLanguages().add(new Language(entry));
                }
            }
        }

        // Константы
        workObjects = workPath.resolve("Constants");
        if (Files.exists(workObjects)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(workObjects, "*.xml")) {
                for (Path entry : entries) {
                    conf.getConstants().add(new Constant(entry));
                }
            }
        }

        // Справочники
        Path workCatalogs = workPath.resolve("Catalogs");
        if (Files.exists(workCatalogs)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(workCatalogs, "*.xml")) {
                for (Path entry : entries) {
                    Catalog catalog = new Catalog(entry);
                    catalog.parse();
                    conf.getCatalogs().add(catalog);
                }
            }
        }

        // Документы
        // Журналы документов

        // Перечисления
        workObjects = workPath.resolve("Enums");
        if (Files.exists(workObjects)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(workObjects, "*.xml")) {
                for (Path entry : entries) {
                    conf.getEnums().add(new Enum(entry));
                }
            }
        }

        // Отчеты

        // Обработки
        workObjects = workPath.resolve("DataProcessors");
        if (Files.exists(workObjects)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(workObjects, "*.xml")) {
                for (Path entry : entries) {
                    conf.getDataProcessors().add(new DataProcessor(entry));
                }
            }
        }

         /*Планы видов характеристик
         Планы счетов
         Планы видов расчета
         Регистры сведений
         Регистры накопления
         Регистры бухгалтерии
         Регистры расчета
         Бизнес-процессы
         Задачи
         Внешние источники данных*/

        return conf;
    }
}
