package ru.pf.metadata.object;

import java.nio.file.Path;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.annotation.RecordSetModule;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CalculationRegister extends AbstractMetadataObject {

    @Forms
    private Set<Form> forms;

    @RecordSetModule
    private Module recordSetModule;

    @ManagerModule
    private Module managerModule; 

    public CalculationRegister(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Регистры расчета";
    }        
}