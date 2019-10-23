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
public class CommonAttribute extends AbstractMetadataObject {

    public CommonAttribute(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Общие реквизиты";
    }        
}