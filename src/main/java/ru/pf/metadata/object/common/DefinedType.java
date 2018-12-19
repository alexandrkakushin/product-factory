package ru.pf.metadata.object.common;

import lombok.Data;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.reader.ObjectReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
public class DefinedType extends AbstractObject<DefinedType> {

    public DefinedType(Path path) {
        super(path);
    }

    @Override
    public void parse() throws IOException {
        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);
        }
    }
}
