package ru.pf.metadata.object;

import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.PlainModule;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Form extends MetadataObject {

    @PlainModule
	private Module module;

    public Form(Path path) {
        super(path);
    }    
}
