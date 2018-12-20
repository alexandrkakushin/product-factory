package ru.pf.metadata.object;

import ru.pf.metadata.reader.ObjectReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author a.kakushin
 */
public class Subsystem extends AbstractObject<Subsystem> {

    private Set<Subsystem> child;

    public Subsystem(Path path) {
        super(path);

        this.child = new LinkedHashSet<>();
    }

    @Override
    public void parse() {
        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);

            // todo: read child
            List<String> childXml = objReader.readChild("/MetaDataObject/Subsystem/ChildObjects");
            for (String item : childXml) {

            }
        }
    }
}
