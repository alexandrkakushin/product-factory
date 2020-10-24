package ru.pf.metadata.reader;

import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.object.ObjectCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Класс-читатель, позволяющий получить множество команд объекта метаданных
 * @author a.kakushin
 */
public class CommandsReader {

    private CommandsReader() {
        throw new IllegalStateException("Utility reader class");
    }

    public static Set<ObjectCommand> read(XmlReader xmlReader, MetadataObject metadataObject) {
        Set<ObjectCommand> result = new HashSet<>();

        String nodeItem = Utils.nodeRoot(metadataObject) + "/ChildObjects/Command";
        List<String> ids = xmlReader.readChild(nodeItem + "/@uuid");
        for (String id : ids) {
            String filterId = nodeItem + "[@uuid='" + id + "']";
            result.add(
                    new ObjectCommand(
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
