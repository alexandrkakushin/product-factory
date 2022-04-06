package ru.pf.metadata.reader;

import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.IMetadataObject;
import ru.pf.metadata.object.MetadataObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс-читатель, позволяет получить множество "владельцев" объекта метаданных
 * @author a.kakushin
 */
public class OwnersReader {

    private OwnersReader() {
        throw  new IllegalStateException("Utility reader class");
    }

    /**
     * Чтение владельцев объекта метаданных
     * @param xmlReader Экземпляр класса для чтения открытого XML-файла
     * @param object Объект метаданных
     * @return Множество объектов метаданных
     * @throws ReaderException Обобщенное исключение парсинга
     */
    public static Set<IMetadataObject> read(XmlReader xmlReader, MetadataObject object) throws ReaderException {
        Set<IMetadataObject> owners = new HashSet<>();

        String nodeRoot = Utils.nodeRoot(object);

        List<String> refOwners = xmlReader.readChild(nodeRoot + "/Properties/Owners/Item");
        if (!refOwners.isEmpty()) {
            Conf conf = object.getConf();
            for (String refOwner : refOwners) {
                IMetadataObject owner = conf.getObjectByRef(refOwner);
                if (owner == null) {
                    throw new ReaderException("Owner not found");
                }
                owners.add(owner);
            }
        }

        return owners;
    }

    /**
     * Чтение владельцев объекта метаданных,
     * инициирует открытие связанного с объектом метаданных XML-файла
     * @param object Объект метаданных
     * @return Множество объектов метаданных
     * @throws ReaderException Обобщенное исключение парсинга
     */
    public static Set<IMetadataObject> read(MetadataObject object) throws ReaderException {
        XmlReader xmlReader = new XmlReader(object.getFile());
        return read(xmlReader, object);
    }
}
