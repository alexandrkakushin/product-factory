package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.CommandModule;

import java.util.UUID;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectCommand extends MetadataObject {

    @CommandModule
    private Module commandModule;

    public ObjectCommand(Conf conf, UUID uuid, String name, String synonym) {
        super(conf, uuid, name, synonym);
    }

    @Override
    public String getListPresentation() {        
        return "Обшие команды";
    }        
}