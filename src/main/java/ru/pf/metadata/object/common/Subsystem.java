package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.reader.ObjectReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

            List<String> childXml = objReader.readChild("/MetaDataObject/Subsystem/ChildObjects/Subsystem");
            for (String item : childXml) {
                Path childPath = fileXml.getParent()
                        .resolve(this.getShortName(fileXml))
                        .resolve("Subsystems")
                        .resolve(item + ".xml");

                Subsystem children = new Subsystem(childPath);
                children.parse();

                this.child.add(children);
            }
        }
    }
}
