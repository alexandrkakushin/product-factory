package ru.pf.metadata.object.common;

import lombok.Data;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.type.Type;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
public class SessionParameter extends AbstractObject<SessionParameter> {

    private Type type;

    public SessionParameter(Path path) {
        super(path);
    }

    @Override
    public void parse() throws IOException {
        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);

            String nodeObject = "/MetaDataObject/" + getMetadataName();
            this.type = new Type(objReader.read(nodeObject + "/Properties/Type/Type"));
        }
    }
}
