package ru.pf.metadata.reader;

import ru.pf.metadata.type.Predefined;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Утилитный класс для чтения предопределенных элементов
 * @author a.kakushin
 */
public class PredefinedReader {

    private PredefinedReader() {
        throw new IllegalStateException("Utility class");
    }

    public static Set<Predefined> read(Path xmlFile) {
        Set<Predefined> result = new HashSet<>();
        if (!Files.exists(xmlFile)) {
            return result;
        }

        String nodeItem = "/PredefinedData/Item";

        XmlReader xmlReader = new XmlReader(xmlFile);
        List<String> ids = xmlReader.readChild(nodeItem + "/@id");
        for (String id : ids) {
            String filterId = nodeItem + "[@id='" + id + "']";

            result.add(
                new Predefined(
                    UUID.fromString(id),
                    xmlReader.read(filterId + "/Name"),
                    xmlReader.read(filterId + "/Code"),
                    xmlReader.read(filterId + "/Description"),
                    xmlReader.readBool(filterId + "/IsFolder")
                )
            );
        }

        return result;
    }
}
