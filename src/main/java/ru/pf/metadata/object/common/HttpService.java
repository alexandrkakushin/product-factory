package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import ru.pf.metadata.object.AbstractMetadataObject;

/**
 * @author a.kakushin
 */
@Data
public class HttpService extends AbstractMetadataObject {

    public HttpService(Path path) {
        super(path);
    }

    @Override
    public String getXmlName() {    
        return "HTTPService";
    }
}