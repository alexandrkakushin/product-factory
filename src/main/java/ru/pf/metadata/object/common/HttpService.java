package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.PlainModule;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.Module;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HttpService extends MetadataObject {

    @PlainModule
    private Module module;

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