package ru.pf.metadata.object;

import ru.pf.metadata.reader.ObjectReader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public class DocumentJournal extends AbstractObject<DocumentJournal> {

    public DocumentJournal(Path path) {
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
