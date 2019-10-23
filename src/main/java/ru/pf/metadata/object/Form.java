package ru.pf.metadata.object;

import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Form extends AbstractMetadataObject {

	private Module module;

    public Form(Path path) {
        super(path);
    }    
}
