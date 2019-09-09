package ru.pf.metadata.object;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.reader.ObjectReader;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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

        return objReader;
    }    
}