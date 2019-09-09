package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import ru.pf.metadata.object.AbstractMetadataObject;

/**
 * @author a.kakushin
 */
@Data
public class DefinedType extends AbstractMetadataObject {

    public DefinedType(Path path) {
        super(path);
    }
}