package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.annotation.Forms;

import java.nio.file.Path;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalDataSource extends MetadataObject {

    @Forms
    private Set<Form> forms;

    public ExternalDataSource(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Внешние источники данных";
    }        
}