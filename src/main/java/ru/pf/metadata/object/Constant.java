package ru.pf.metadata.object;

import ru.pf.metadata.Module;

import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public class Constant extends AbstractObject<Constant> {

    private Module valueManagerModule;

    public Constant(Path path) {
        super(path);
    }

    @Override
    public void parse() {

    }
}
