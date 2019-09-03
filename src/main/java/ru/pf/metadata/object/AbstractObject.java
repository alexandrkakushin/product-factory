package ru.pf.metadata.object;

import java.nio.file.Path;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.object.common.HttpService;
import ru.pf.metadata.object.common.XdtoPackage;

/**
 * @author a.kakushin
 */
@Data
public abstract class AbstractObject<T> implements MetadataObject<T> {

    @JsonIgnore
    private Path file;

    @JsonView(MetadataJsonView.List.class)
    private UUID uuid;

    @JsonView(MetadataJsonView.List.class)
    private String name;

    @JsonView(MetadataJsonView.List.class)
    private String synonym;
    private String comment;

    public AbstractObject() {
    }

    public AbstractObject(Path file) {
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

    public static String getMetadataName(Class<?> objClass) {
        if (objClass.equals(XdtoPackage.class)) {
            return "XDTOPackage";
        } else if (objClass.equals(HttpService.class)) {
            return "HTTPService";
        }

        return objClass.getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractObject<?> object = (AbstractObject<?>) o;

        return uuid != null ? uuid.equals(object.uuid) : object.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
