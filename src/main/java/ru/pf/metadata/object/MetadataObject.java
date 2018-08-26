package ru.pf.metadata.object;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public interface MetadataObject<T> {

    void parse() throws IOException;

    default String getShortName(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
