package ru.pf.metadata.object.common;

import java.io.IOException;
import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.type.Type;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SessionParameter extends AbstractMetadataObject {

    private Type type;

    public SessionParameter(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Параметры сеанса";
    }    

    @Override
    public ObjectReader parse() throws IOException {
        ObjectReader objReader = super.parse();

        String nodeObject = "/MetaDataObject/" + getXmlName();
        this.type = new Type(objReader.read(nodeObject + "/Properties/Type/Type"));

        return objReader;
    }
}