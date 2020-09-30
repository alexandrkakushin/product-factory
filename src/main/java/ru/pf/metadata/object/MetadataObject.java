package ru.pf.metadata.object;

import com.fasterxml.jackson.annotation.JsonView;
import ru.pf.metadata.MetadataJsonView;

import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public interface MetadataObject {

    @JsonView({ MetadataJsonView.List.class })
    default String getXmlName() {
        return this.getClass().getSimpleName();
    }

    default String getListPresentation() {
        return this.getXmlName();
    }

    default String getShortName(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}