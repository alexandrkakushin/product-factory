package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.metadata.reader.Utils;
import ru.pf.metadata.reader.XmlReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Объект метаданных "Роль"
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends MetadataObject {

    /**
     * Устанавливать права для новых объектов
     */
    private boolean setForNewObjects;

    /**
     * Устанавливать права для реквизитов и табличных частей по умолчанию
     */
    private boolean setForAttributesByDefault;

    /**
     * Независимые права подчиненных объектов
     */
    private boolean independentRightsOfChildObjects;

    /**
     * Объекты метаданных с набором установленных прав
     */
    private Set<RightObject> rightObjects;

    public Role(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Роли";
    }

    @Override
    public ObjectReader parse() throws ReaderException {
        ObjectReader objReader = super.parse();

        Path pathExt = Utils.getExt(this);
        if (Files.exists(pathExt)) {
            Path fileRights = pathExt.resolve("Rights.xml");
            if (Files.exists(fileRights)) {
                XmlReader xmlReader = new XmlReader(fileRights);

                this.setForNewObjects  = xmlReader.readBool("Rights/setForNewObjects");
                this.setForAttributesByDefault = xmlReader.readBool("Rights/setForAttributesByDefault");
                this.independentRightsOfChildObjects = xmlReader.readBool("Rights/independentRightsOfChildObjects");

                this.rightObjects = new HashSet<>();

                List<String> childrenObject = xmlReader.readChild("Rights/object/name");
                for (String child : childrenObject) {
                    Set<Right> rights = new HashSet<>();

                    List<String> rightNames = xmlReader.readChild("Rights/object[name = '" + child + "']/right/name");
                    for (String rightName : rightNames) {
                        // True (по умолчанию)
                        rights.add(new Right(Right.Name.valueByString(rightName), true));
                    }
                    this.rightObjects.add(new RightObject(child, rights));
                }
            }
        }

        return objReader;
    }

    /**
     * Набор прав объекта метаданных (имя класса сохранено для соответствия с XML-файлами 1С)
     */
    @Getter
    public static class RightObject {

        /**
         * Объект метаданных
         */
        private final String metadataObject;

        /**
         * Набор прав
         */
        private final Set<Right> rights;

        public RightObject(String metadataObject, Set<Right> rights) {
            this.metadataObject = metadataObject;
            this.rights = rights;
        }
    }

    /**
     * Право над объектом метаданных
     */
    @Getter
    public static class Right {

        /**
         * Имя права
         */
        private final Name name;

        /**
         * Доступность
         */
        private final boolean value;

        public Right(Name name, boolean value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Имена прав над объектами метаданных
         */
        public enum Name {

            // Конфигурация

            /**
             * Администрирование
             */
            ADMINISTRATION,

            /**
             * Администрирование данных
             */
            DATA_ADMINISTRATION,

            /**
             * Обновление конфигурации базы данных
             */
            UPDATE_DATABASE_CONFIGURATION,

            /**
             * Монопольный режим
             */
            EXCLUSIVE_MODE,

            /**
             * Активные пользователи
             */
            ACTIVE_USERS,

            /**
             * Режим "Все функции"
             */
            ALL_FUNCTIONS_MODE,

            /**
             * Журнал регистрации
             */
            EVENT_LOG,

            /**
             * Тонкий клиент
             */
            THIN_CLIENT,

            /**
             * Web-клиент
             */
            WEB_CLIENT,

            /**
             * Мобильный клиент
             */
            MOBILE_CLIENT,

            /**
             * Толстый клиент
             */
            THICK_CLIENT,

            /**
             * Внешнее соединение
             */
            EXTERNAL_CONNECTION,

            /**
             * Automation
             */
            AUTOMATION,

            /**
             * Режим технического специалиста
             */
            TECHNICAL_SPECIALIST_MODE,

            /**
             * Регистрация системы взаимодействия
             */
            COLLABORATION_SYSTEM_INFO_BASE_REGISTRATION,

            /**
             * Режим основного окна "Обычный"
             */
            MAIN_WINDOW_MODE_NORMAL,

            /**
             * Режим основного окна "Рабочее место"
             */
            MAIN_WINDOW_MODE_WORKPLACE,

            /**
             * Режим основного окна "Встроенное рабочее место"
             */
            MAIN_WINDOW_MODE_EMBEDDED_WORKPLACE,

            /**
             * Режим основного окна "Полноэкранное место"
             */
            MAIN_WINDOW_MODE_FULLSCREEN_WORKPLACE,

            /**
             * Режим основного окна "Киоск"
             */
            MAIN_WINDOW_MODE_KIOSK,

            /**
             * Клиент системы аналитики
             */
            ANALYTICS_SYSTEM_CLIENT,

            /**
             * Сохранение данных пользователя
             */
            SAVE_USER_DATA,

            /**
             * Администрирование расширений конфигурации
             */
            CONFIGURATION_EXTENSIONS_ADMINISTRATION,

            /**
             * Интерактивное открытие внешних обработок
             */
            INTERACTIVE_OPEN_EXT_DATA_PROCESSORS,

            /**
             * Интерактивное открытие внешних отчетов
             */
            INTERACTIVE_OPEN_EXT_REPORTS,

            /**
             * Вывод
             */
            OUTPUT,

            // Параметры сеансов

            /**
             * Получение
             */
            GET,

            /**
             * Установка
             */
            SET,

            READ,
            INSERT,
            UPDATE,
            DELETE,
            POSTING,
            UNDO_POSTING,
            VIEW,
            EDIT,
            USE,
            INPUT_BY_STRING,

            INTERACTIVE_INSERT,
            INTERACTIVE_SET_DELETION_MARK,
            INTERACTIVE_CLEAR_DELETION_MARK,
            INTERACTIVE_DELETE_MARKED,
            INTERACTIVE_POSTING,
            INTERACTIVE_POSTING_REGULAR,
            INTERACTIVE_UNDO_POSTING,
            INTERACTIVE_CHANGE_OF_POSTED;

            public static Name valueByString(String value) {
                return Arrays.stream(values())
                        .filter(enumValue -> enumValue.name().replace("_", "").equalsIgnoreCase(value))
                        .findFirst()
                        .orElse(null);
            }
        }
    }
}