package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import ru.pf.metadata.object.AbstractMetadataObject;

/**
 * @author a.kakushin
 */
@Data
public class ScheduledJob extends AbstractMetadataObject {

    public ScheduledJob(Path path) {
        super(path);
    }
}