package ru.pf.metadata.object;

import java.nio.file.Path;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.behavior.Forms;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataProcessor extends AbstractMetadataObject {

    public DataProcessor(Path path) {
        super(path);
    }

    @Forms
    Set<Form> forms;

}