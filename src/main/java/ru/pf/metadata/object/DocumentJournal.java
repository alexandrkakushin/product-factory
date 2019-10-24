package ru.pf.metadata.object;

import java.nio.file.Path;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentJournal extends AbstractMetadataObject {

    @Forms
    private Set<Form> forms;

    @ManagerModule
    private Module managerModule;

    public DocumentJournal(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Журналы документов";
    }        
}
