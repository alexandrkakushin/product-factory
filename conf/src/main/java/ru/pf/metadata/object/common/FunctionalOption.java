package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.MetadataObject;

import java.nio.file.Path;


/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionalOption extends MetadataObject {

    public FunctionalOption(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Функциональные опции";
    }        
}