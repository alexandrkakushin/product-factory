package ru.pf.metadata.object.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.MetadataObject;

import java.nio.file.Path;


/**
 * Объект метаданных "Общий реквизит"
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonAttribute extends MetadataObject {

    /**
     * Конструктор с указанием XML-файла
     * @param xmlFile Распложение XML-файла
     */
    public CommonAttribute(Path xmlFile) {
        super(xmlFile);
    }

    @Override
    public String getListPresentation() {        
        return "Общие реквизиты";
    }        
}