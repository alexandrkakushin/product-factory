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
public class AccountingRegister extends AbstractMetadataObject {

    @Forms
    private Set<Form> forms;

    public AccountingRegister(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Регистры бухгалтерии";
    }    
}
