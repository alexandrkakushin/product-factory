package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.metadata.reader.XmlReader;
import ru.pf.metadata.type.Type;

import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SessionParameter extends MetadataObject {

    private Type type;

    public SessionParameter(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Параметры сеанса";
    }    

    @Override
    public ObjectReader parse() throws ReaderException {
        ObjectReader objReader = super.parse();
        XmlReader xmlReader = objReader.getXmlReader();

        String nodeObject = "/MetaDataObject/" + getXmlName();
        this.type = new Type(xmlReader.read(nodeObject + "/Properties/Type/Type"));

        return objReader;
    }
}