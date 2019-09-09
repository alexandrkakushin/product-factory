package ru.pf.metadata.object;

import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public class ExternalDataSource extends AbstractMetadataObject {

    public ExternalDataSource(Path path) {
        super(path);
    }
}