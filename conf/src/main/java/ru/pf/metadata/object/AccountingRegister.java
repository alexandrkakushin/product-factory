package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.annotation.RecordSetModule;

import java.nio.file.Path;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountingRegister extends MetadataObject {

    @Forms
    private Set<Form> forms;

    @RecordSetModule
    private Module recordSetModule;

    @ManagerModule
    private Module managerModule; 

    public AccountingRegister(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Регистры бухгалтерии";
    }    
}
