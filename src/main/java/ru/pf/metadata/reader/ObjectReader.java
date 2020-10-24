package ru.pf.metadata.reader;

import ru.pf.metadata.annotation.*;
import ru.pf.metadata.object.MetadataObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author a.kakushin
 */
public class ObjectReader {

    private final MetadataObject metadataObject;
    private final XmlReader xmlReader;

    public ObjectReader(MetadataObject metadataObject) {
        this.metadataObject = metadataObject;
        this.xmlReader = new XmlReader(metadataObject.getFile());
    }

    public MetadataObject getMetadataObject() {
        return this.metadataObject;
    }

    public XmlReader getXmlReader() {
        return this.xmlReader;
    }

    public void read(boolean onlyHeaders) throws ReaderException {
        Path pathExt = this.metadataObject.getFile()
                .getParent()
                .resolve(this.metadataObject.getShortName(this.metadataObject.getFile()))
                .resolve("Ext");

        fillHeaders();
        if (onlyHeaders) {
            return;
        }

        try {
            for (Field field : this.metadataObject.getClass().getDeclaredFields()) {
                Object value = null;

                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    Class<?> clazz = annotation.annotationType();
                    if (clazz == Forms.class) {
                        value = FormsReader.read(this.xmlReader, this.metadataObject);

                    } else if (clazz == Owners.class) {
                        value = OwnersReader.read(this.xmlReader, this.metadataObject);

                    } else if (clazz == Commands.class) {
                        value = CommandsReader.read(this.xmlReader, this.metadataObject);

                    } else if (Files.exists(pathExt)) {
                        // Modules
                        if (clazz == CommandModule.class
                                || clazz == ObjectModule.class
                                || clazz == ManagerModule.class
                                || clazz == PlainModule.class
                                || clazz == RecordSetModule.class
                                || clazz == ValueManagerModule.class) {
                            value = ModuleReader.read(pathExt, clazz);

                        } else if (clazz == Predefined.class) {
                            value = PredefinedReader.read(pathExt.resolve("Predefined.xml"));
                        }
                    }
                }

                if (value != null) {
                    Optional<Method> setterMethod = Stream.of(metadataObject.getClass().getMethods())
                            .filter(method1 -> method1.getName().equalsIgnoreCase("set" + field.getName()))
                            .findFirst();

                    setterMethod
                            .orElseThrow(() -> new RuntimeException("No setter found for field: " + field.getName()))
                            .invoke(this.metadataObject, value);
                }
            }

        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            throw new ReaderException(e.getMessage());
        }
    }

    private void fillHeaders() {
        String nodeObject = Utils.nodeRoot(this.metadataObject);
        metadataObject.setUuid(xmlReader.readUUID(nodeObject + "/@uuid"));
        metadataObject.setName(xmlReader.read(nodeObject+ "/Properties/Name"));
        metadataObject.setSynonym(xmlReader.read(nodeObject + "/Properties/Synonym/item/content"));
        metadataObject.setComment(xmlReader.read(nodeObject+ "/Properties/Comment"));
    }
}
