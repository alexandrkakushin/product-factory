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
public class WebService extends MetadataObject {

    @PlainModule
    private Module module;

    public WebService(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Web-сервисы";
    }        
}