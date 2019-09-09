package ru.pf.metadata.object;

import java.nio.file.Path;

import ru.pf.metadata.Module;

/**
 * @author a.kakushin
 */
public class Constant extends AbstractMetadataObject {

    private Module valueManagerModule;

    public Constant(Path path) {
        super(path);
    }
}
