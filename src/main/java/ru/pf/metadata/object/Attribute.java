package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.type.Type;

import java.util.UUID;

/**
 * Реквизит
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Attribute extends MetadataObject {

    private Type type;

    public Attribute(Conf conf, UUID uuid, String name, String synonym) {
        super(conf, uuid, name, synonym);
    }
}
