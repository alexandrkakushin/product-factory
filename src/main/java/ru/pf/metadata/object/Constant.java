package ru.pf.metadata.object;

import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.ValueManagerModule;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Constant extends MetadataObject {

    @ValueManagerModule
    private Module valueManagerModule;

    public Constant(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Константы";
    }        
}
