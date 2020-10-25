package ru.pf.metadata.reader;

import ru.pf.metadata.object.Attribute;
import ru.pf.metadata.object.MetadataObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Класс-читатель, позволяющий получить множество реквизитов объекта метаданных
 * @author a.kakushin
 */
public class AttributesReader {

    private AttributesReader() {
        throw new IllegalStateException("Utility reader class");
    }

    /**
     * Чтение реквизитов объекта метаданных
     * @param xmlReader Экземпляр класса для чтения открытого XML-файла
     * @param metadataObject Объект метаданных, например Справочник, Документ и т.д.
     * @return Множество атрибутов
     */
    public static Set<Attribute> read(XmlReader xmlReader, MetadataObject metadataObject) {
        Set<Attribute> result = new HashSet<>();

        String nodeItem = Utils.nodeRoot(metadataObject) + "/ChildObjects/Attribute";
        List<String> ids = xmlReader.readChild(nodeItem + "/@uuid");
        for (String id : ids) {
            String filterId = nodeItem + "[@uuid='" + id + "']";
            result.add(
                    new Attribute(
                            metadataObject.getConf(),
                            UUID.fromString(id),
                            xmlReader.read(filterId + "/Properties/Name"),
                            xmlReader.read(filterId + "/Properties/Synonym/item/content")
                    )
            );
        }
        return result;

    }
}
