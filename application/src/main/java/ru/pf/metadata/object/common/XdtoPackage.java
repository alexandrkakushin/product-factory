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
public class XdtoPackage extends MetadataObject {

    public XdtoPackage(Path path) {
        super(path);
    }

    @Override
    public String getXmlName() {
        return "XDTOPackage";
    }

    @Override
    public String getListPresentation() {        
        return "XDTO-пакеты";
    }        
}