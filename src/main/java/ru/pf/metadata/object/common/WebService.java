package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.AbstractMetadataObject;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WebService extends AbstractMetadataObject {

    public WebService(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Web-сервисы";
    }        
}