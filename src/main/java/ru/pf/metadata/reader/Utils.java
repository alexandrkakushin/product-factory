package ru.pf.metadata.reader;

import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.object.common.HttpService;
import ru.pf.metadata.object.common.XdtoPackage;
import ru.pf.metadata.type.Attribute;
import ru.pf.metadata.type.TabularSection;

import java.nio.file.Path;

/**
 * Вспомогательгый класс для парсинга конфигурации
 * @author a.kakushin
 */
public class Utils {

    private Utils() {
        throw  new IllegalStateException("Utility class");
    }

    /**
     * Получение имени типа данных по классу объекта метаданных
     * @param clazz Класс объекта метаданных
     * @return Имя типа данных
     */
    public static String getRefType(Class<?> clazz) {
        // При разборе XML-файла конфигурации имена узлов не соответсвуют
        // именам Java-классов

        String nodeName;
        if (clazz.equals(XdtoPackage.class)) {
            nodeName = "XDTOPackage";
        } else if (clazz.equals(HttpService.class)) {
            nodeName = "HTTPService";
        } else {
            nodeName = clazz.getSimpleName();
        }

        return nodeName;
    }

    /**
     * Получение пути корневого XML-узла
     * @param object Объект метаданных
     * @return Строка
     */
    public static String nodeRoot(MetadataObject object) {
        if (object instanceof TabularSection) {
            return "/MetaDataObject/" + object.getParent().getXmlName() + "/ChildObjects/TabularSection[@uuid='" + object.getUuid() + "']";

        } else if (object instanceof Attribute) {
            return nodeRoot(object.getParent()) + "/ChildObjects/Attribute[@uuid='" + object.getUuid() + "']";

        } else {
            return "/MetaDataObject/" + object.getXmlName();
        }
    }

    /**
     * Каталог "Ext" объекта метаданных
     * @param metadataObject Объект метаданных
     * @return Path Каталог
     */
    public static Path getExt(MetadataObject metadataObject) {
        return metadataObject.getFile()
                .getParent()
                .resolve(metadataObject.getShortName(metadataObject.getFile()))
                .resolve("Ext");
    }
}
