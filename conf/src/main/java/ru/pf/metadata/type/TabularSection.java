package ru.pf.metadata.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.Attributes;
import ru.pf.metadata.object.MetadataObject;

import java.util.Set;
import java.util.UUID;

/**
 * Табличная часть
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TabularSection extends MetadataObject {

    @Attributes
    private Set<Attribute> attributes;

    public TabularSection(MetadataObject parent, UUID uuid) {
        super(parent, uuid);
    }
}
