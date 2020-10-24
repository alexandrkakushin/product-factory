package ru.pf.metadata.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.ReaderException;

import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

/**
 * @author a.kakushin
 */
@Data
public abstract class MetadataObject implements IMetadataObject {

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

    MetadataObject() {
    }

    protected MetadataObject(Path file) {
        this();
        this.file = file;
    }

    public MetadataObject(Conf conf, UUID uuid, String name, String synonym) {
        this.conf = conf;
        this.uuid = uuid;
        this.name = name;
        this.synonym = synonym;
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
        MetadataObject object = (MetadataObject) o;
        return Objects.equals(uuid, object.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    public ObjectReader parse() throws ReaderException {
        ObjectReader objReader = new ObjectReader(this);
        objReader.read(false);

        return objReader;
    }
}