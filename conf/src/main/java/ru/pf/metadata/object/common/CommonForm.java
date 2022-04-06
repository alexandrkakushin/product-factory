package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.Form;

import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonForm extends Form {

    public CommonForm(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {
        return "Общие формы";
    }
}
