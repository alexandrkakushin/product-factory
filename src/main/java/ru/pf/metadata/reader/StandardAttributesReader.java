package ru.pf.metadata.reader;

import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.type.StandardAttribute;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс-читатель, позволяющий получить множество стандартных реквизитов объекта метаданных
 * @author a.kakushin
 */
public class StandardAttributesReader {

    private StandardAttributesReader() {
        throw new IllegalStateException("Utility reader class");
    }

    /**
     * Чтение стандартных реквизитов объекта метаданных
     * @param xmlReader Экземпляр класса для чтения открытого XML-файла
     * @param metadataObject Объект метаданных, например Справочник, Документ и т.д.
     * @return Множество атрибутов
     */
    public static Set<StandardAttribute> read(XmlReader xmlReader, MetadataObject metadataObject) {
        Set<StandardAttribute> result = new HashSet<>();

        String nodeItem = Utils.nodeRoot(metadataObject) + "/Properties/StandardAttributes/StandardAttribute";

        List<String> names = xmlReader.readChild(nodeItem + "/@name");
        for (String name : names) {
            StandardAttribute attribute = new StandardAttribute(name);
            result.add(attribute);
        }
        return result;
    }
}
