package ru.pf.metadata.object;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.annotation.CommandModule;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.annotation.ObjectModule;
import ru.pf.metadata.annotation.PlainModule;
import ru.pf.metadata.annotation.RecordSetModule;
import ru.pf.metadata.annotation.ValueManagerModule;
import ru.pf.metadata.reader.ModuleReader;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.Module;

/**
 * @author a.kakushin
 */
@Data
public abstract class AbstractMetadataObject implements MetadataObject {

    @JsonIgnore
    private Path file;

    @JsonView(MetadataJsonView.List.class)
    private UUID uuid;

    @JsonView(MetadataJsonView.List.class)
    private String name;

    @JsonView(MetadataJsonView.List.class)
    private String synonym;
    private String comment;

    public AbstractMetadataObject() {
    }

    public AbstractMetadataObject(Path file) {
        this();
        this.file = file;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbstractMetadataObject object = (AbstractMetadataObject) o;

        return uuid != null ? uuid.equals(object.uuid) : object.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public ObjectReader parse() throws IOException {
        Path fileXml = this.getFile().getParent().resolve(this.getFile());
        ObjectReader objReader = new ObjectReader(fileXml);
        objReader.fillCommonField(this);

        Path pathExt = this.getFile()
            .getParent()
            .resolve(this.getShortName(this.getFile()))
            .resolve("Ext");

        // annotations
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                // Forms
                if (field.isAnnotationPresent(Forms.class)) {                    
                    field.set(this, objReader.readForms(this));

                } else {
                    if (Files.exists(pathExt)) {
                        // Modules
                        if (field.isAnnotationPresent(CommandModule.class)) {
                            field.set(this, ModuleReader.read(pathExt.resolve("CommandModule.bsl"),
                                Module.Type.COMMAND_MODULE));

                        } else if (field.isAnnotationPresent(ObjectModule.class)) {
                            field.set(this, ModuleReader.read(pathExt.resolve("ObjectModule.bsl"),
                                Module.Type.OBJECT_MODULE));

                        } else if (field.isAnnotationPresent(ManagerModule.class)) {
                            field.set(this, ModuleReader.read(pathExt.resolve("ManagerModule.bsl"),
                                Module.Type.MANAGER_MODULE));

                        } else if (field.isAnnotationPresent(PlainModule.class)) {
                            field.set(this, ModuleReader.read(pathExt.resolve("Module.bsl"),
                                Module.Type.PLAIN_MODULE));

                        } else if (field.isAnnotationPresent(RecordSetModule.class)) {
                            field.set(this, ModuleReader.read(pathExt.resolve("RecordSetModule.bsl"),
                            Module.Type.RECORD_SET_MODULE));

                        } else if (field.isAnnotationPresent(ValueManagerModule.class)) {
                            field.set(this, ModuleReader.read(pathExt.resolve("ValueManagerModule.bsl"),
                            Module.Type.VALUE_MANAGER_MODULE));
                        }     
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }            
        }

        return objReader;
    }    
}