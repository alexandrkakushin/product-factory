package ru.pf.metadata.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.MetadataObject;

import java.util.UUID;

/**
 * Реквизит
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Attribute extends MetadataObject {

    private Type type;

    public Attribute(MetadataObject parent, UUID uuid) {
        super(parent, uuid);
    }
}
