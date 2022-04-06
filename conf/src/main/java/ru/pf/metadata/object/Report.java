package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.annotation.ObjectModule;

import java.nio.file.Path;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Report extends MetadataObject {

    @Forms
    private Set<Form> forms;

    @ObjectModule
    private Module objectModule;

    @ManagerModule
    private Module managerModule;
   
    public Report(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Отчеты";
    }        
}