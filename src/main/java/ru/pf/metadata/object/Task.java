package ru.pf.metadata.object;

import java.nio.file.Path;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.annotation.ObjectModule;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends AbstractMetadataObject {

    @Forms
    private Set<Form> forms;

    @ObjectModule
    private Module objectModule;

    @ManagerModule
    private Module managerModule;

    public Task(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Задачи";
    }        
}