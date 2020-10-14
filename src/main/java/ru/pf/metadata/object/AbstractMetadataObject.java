package ru.pf.metadata.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.*;
import ru.pf.metadata.reader.ModuleReader;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.PredefinedReader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author a.kakushin
 */
@Data
public abstract class AbstractMetadataObject implements MetadataObject {

    @JsonIgnore
    private Conf conf;

    @JsonIgnore
    private Path file;

    @JsonView(MetadataJsonView.List.class)
    private UUID uuid;

    @JsonView(MetadataJsonView.List.class)
    private String name;

    @JsonView(MetadataJsonView.List.class)
    private String synonym;
    private String comment;

    AbstractMetadataObject() {
    }

    protected AbstractMetadataObject(Path file) {
        this();
        this.file = file;
    }

    public Conf getConf() {
        return this.conf;
    }

    // TODO: вынести в конструктор
    public void setConf(Conf conf) {
        this.conf = conf;
    }

    public Path getFile() {
        return file;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        AbstractMetadataObject object = (AbstractMetadataObject) o;
        return Objects.equals(uuid, object.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    public ObjectReader parse() throws IOException {
        ObjectReader objReader = new ObjectReader(this);
        objReader.fillCommonField();

        Path pathExt = this.getFile()
                .getParent()
                .resolve(this.getShortName(this.getFile()))
                .resolve("Ext");

        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                Object value = null;

                if (field.isAnnotationPresent(Forms.class)) {
                    value = objReader.readForms();

                } else if (field.isAnnotationPresent(Owners.class)) {
                    value =  objReader.readOwners();

                } else if (Files.exists(pathExt)) {
                    // Modules
                    if (field.isAnnotationPresent(CommandModule.class)) {
                        value = ModuleReader.read(pathExt.resolve("CommandModule.bsl"),
                            Module.Type.COMMAND_MODULE);

                    } else if (field.isAnnotationPresent(ObjectModule.class)) {
                        value = ModuleReader.read(pathExt.resolve("ObjectModule.bsl"),
                            Module.Type.OBJECT_MODULE);

                    } else if (field.isAnnotationPresent(ManagerModule.class)) {
                        value = ModuleReader.read(pathExt.resolve("ManagerModule.bsl"),
                            Module.Type.MANAGER_MODULE);

                    } else if (field.isAnnotationPresent(PlainModule.class)) {
                        value = ModuleReader.read(pathExt.resolve("Module.bsl"),
                            Module.Type.PLAIN_MODULE);

                    } else if (field.isAnnotationPresent(RecordSetModule.class)) {
                        value = ModuleReader.read(pathExt.resolve("RecordSetModule.bsl"),
                            Module.Type.RECORD_SET_MODULE);

                    } else if (field.isAnnotationPresent(ValueManagerModule.class)) {
                        value = ModuleReader.read(pathExt.resolve("ValueManagerModule.bsl"),
                            Module.Type.VALUE_MANAGER_MODULE);

                    } else if (field.isAnnotationPresent(Predefined.class)) {
                        value = PredefinedReader.read(pathExt.resolve("Predefined.xml"));
                    }
                }

                if (value != null) {
                    Optional<Method> setterMethod = Stream.of(getClass().getMethods())
                            .filter(method1 -> method1.getName().equalsIgnoreCase("set" + field.getName()))
                            .findFirst();

                    setterMethod
                            .orElseThrow(() -> new RuntimeException("No setter found for field: " + field.getName()))
                            .invoke(this, value);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            // TODO: выбросить дальше исключение
            e.printStackTrace();
        }

        return objReader;
    }
}