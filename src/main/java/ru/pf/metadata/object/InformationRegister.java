package ru.pf.metadata.object;

import ru.pf.metadata.reader.ObjectReader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public class InformationRegister extends AbstractObject<InformationRegister> {

    public InformationRegister(Path path) {
        super(path);
    }

    @Override
    public void parse() {
        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);
        }
    }
}
