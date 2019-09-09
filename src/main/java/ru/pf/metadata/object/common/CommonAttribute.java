package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import ru.pf.metadata.object.AbstractMetadataObject;

/**
 * @author a.kakushin
 */
@Data
public class CommonAttribute extends AbstractMetadataObject {

    public CommonAttribute(Path path) {
        super(path);
    }
}