package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.metadata.reader.XmlReader;

import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Language extends MetadataObject {

    private String languageCode;

    public Language(Path path) {
        super(path);
    }

    @Override
    public ObjectReader parse() throws ReaderException {
        ObjectReader objReader = super.parse();
        XmlReader xmlReader = objReader.getXmlReader();

        String nodeProperties = "/MetaDataObject/" + getXmlName() + "/Properties/";
        this.languageCode  = xmlReader.read(nodeProperties + "LanguageCode");
        
        return objReader;
    }

    @Override
    public String getListPresentation() {
        return "Языки";
    }
}