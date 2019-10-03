package ru.pf.metadata.object.common;

import java.io.IOException;
import java.nio.file.Path;

import lombok.Data;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.reader.ObjectReader;

/**
 * @author a.kakushin
 */
@Data
public class Language extends AbstractMetadataObject {

    private String languageCode;

    public Language(Path path) {
        super(path);
    }

    @Override
    public ObjectReader parse() throws IOException {
        ObjectReader objReader = super.parse();

        String nodeProperties = "/MetaDataObject/" + getXmlName() + "/Properties/";
        this.languageCode  = objReader.read(nodeProperties + "LanguageCode");
        
        return objReader;
    }
}