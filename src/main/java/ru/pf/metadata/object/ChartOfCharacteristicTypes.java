package ru.pf.metadata.object;

import java.io.IOException;
import java.nio.file.Path;

import ru.pf.metadata.reader.ObjectReader;

/**
 * @author a.kakushin
 */
public class ChartOfCharacteristicTypes extends AbstractMetadataObject {

    public ChartOfCharacteristicTypes(Path path) {
        super(path);
    }

    @Override
    public ObjectReader parse() throws IOException {
        return super.parse();
    }
}