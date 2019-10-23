package ru.pf.metadata.object;

import java.nio.file.Path;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.Forms;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends AbstractMetadataObject {

    @Forms
    private Set<Form> forms;

    public Task(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Задачи";
    }        
}