package ru.pf.metadata.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.nio.file.Path;
import java.util.UUID;

/**
 * @author a.kakushin
 */
@Data
public abstract class AbstractObject<T> implements MetadataObject<T> {

    @JsonIgnore
    private Path file;
    private UUID uuid;

    private String name;
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
}
