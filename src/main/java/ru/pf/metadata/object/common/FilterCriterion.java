package ru.pf.metadata.object.common;

import java.nio.file.Path;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.object.Form;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FilterCriterion extends AbstractMetadataObject {

    @Forms
    private Set<Form> forms;

    public FilterCriterion(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Критерии отбора";
    }        
}