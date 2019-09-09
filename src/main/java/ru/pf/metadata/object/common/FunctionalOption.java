package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import ru.pf.metadata.object.AbstractMetadataObject;

/**
 * @author a.kakushin
 */
@Data
public class FunctionalOption extends AbstractMetadataObject {

    public FunctionalOption(Path path) {
        super(path);
    }
}