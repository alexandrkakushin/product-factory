package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.CommandModule;
import ru.pf.metadata.object.MetadataObject;

import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonCommand extends MetadataObject {

    @CommandModule
    private Module commandModule;

    public CommonCommand(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Обшие команды";
    }        
}