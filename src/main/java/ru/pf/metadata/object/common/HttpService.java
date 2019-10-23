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
public class HttpService extends AbstractMetadataObject {

    public HttpService(Path path) {
        super(path);
    }

    @Override
    public String getXmlName() {    
        return "HTTPService";
    }

    @Override
    public String getListPresentation() {        
        return "HTTP-сервисы";
    }        
}