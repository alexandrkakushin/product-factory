package ru.pf.metadata.reader;

import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.type.TabularSection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Класс-читатель, позволяющий получить множество табличных частей объекта метаданных
 * @author a.kakushin
 */
public class TabularSectionsReader {

    private TabularSectionsReader() {
        throw new IllegalStateException("Utility reader class");
    }

    /**
     * Чтение табличных частей объекта метаданных
     * @param xmlReader Экземпляр класса для чтения открытого XML-файла
     * @param metadataObject Объект метаданных, например Справочник, Документ и т.д.
     * @return Множество табличных частей
     * @throws ReaderException Обобщенное исключение парсинга
     */
    public static Set<TabularSection> read(XmlReader xmlReader, MetadataObject metadataObject) throws ReaderException {
        Set<TabularSection> result = new HashSet<>();

        String nodeItem = Utils.nodeRoot(metadataObject) + "/ChildObjects/TabularSection";

        List<String> ids = xmlReader.readChild(nodeItem + "/@uuid");
        for (String id : ids) {
            TabularSection tabularSection = new TabularSection(metadataObject, UUID.fromString(id));
            tabularSection.parse();

            result.add(tabularSection);
        }

        return result;
    }
}
