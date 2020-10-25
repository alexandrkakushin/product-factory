package ru.pf.metadata.object;

import lombok.Data;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.metadata.type.Type;

import java.util.UUID;

/**
 * Реквизит
 * @author a.kakushin
 */
@Data
public class Attribute extends MetadataObject {

    private Type type;

    public Attribute(Conf conf, UUID uuid, String name, String synonym) {
        super(conf, uuid, name, synonym);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Attribute attribute = (Attribute) o;

        return type != null ? type.equals(attribute.type) : attribute.type == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public ObjectReader parse() throws ReaderException {
        return super.parse();
    }
}
