package ru.pf.metadata.object.common;

import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.MetadataObject;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends MetadataObject {

    public Role(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Роли";
    }        
}