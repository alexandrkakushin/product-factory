package ru.pf.metadata.object.common;

import lombok.Data;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.reader.ObjectReader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
public class Language extends AbstractObject<Language> {

    private String languageCode;

    public Language(Path path) {
        super(path);
    }

    @Override
    public void parse() {
        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);

            String nodeProperties = "/MetaDataObject/" + getMetadataName() + "/Properties/";
            this.languageCode  = objReader.read(nodeProperties + "LanguageCode");
        }
    }

    @Override
    public String getMetadataSynonym() {
        return super.getMetadataSynonym();
    }
}