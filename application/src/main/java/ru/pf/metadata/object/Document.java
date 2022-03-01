package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.*;
import ru.pf.metadata.type.Attribute;
import ru.pf.metadata.type.TabularSection;

import java.nio.file.Path;
import java.util.Set;

/**
 * Объек метаданных "Документ"
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Document extends MetadataObject {

    /**
     * Стандартные реквизиты
     */
    @StandardAttributes
    private Set<Attribute> standardAttributes;

    /**
     * Реквизиты
     */
    @Attributes
    private Set<Attribute> attributes;

    /**
     * Табличные части
     */
    @TabularSections
    private Set<TabularSection> tabularSections;

    /**
     * Формы
     */
    @Forms
    private Set<Form> forms;

    /**
     * Модуль объекта
     */
    @ObjectModule
    private Module objectModule;

    /**
     * Модуль менеджера
     */
    @ManagerModule
    private Module managerModule;

    public Document(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {        
        return "Документы";
    }        
}
