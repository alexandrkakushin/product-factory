package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.object.Form;
import ru.pf.metadata.object.MetadataObject;

import java.nio.file.Path;
import java.util.Set;


/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SettingsStorage extends MetadataObject {

    @Forms
    private Set<Form> forms;

    @ManagerModule
    private Module managerModule;

    public SettingsStorage(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Хранилища настроек";
    }        
}