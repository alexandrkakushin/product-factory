package ru.pf.metadata.object.common;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.reader.ObjectReader;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Subsystem extends AbstractMetadataObject {

    private Set<String> items;
    private Set<Subsystem> child;

    public Subsystem(Path path) {
        super(path);
        this.child = new LinkedHashSet<>();
        this.items = new LinkedHashSet<>();
    }

    public Set<String> getItems() {
        return items;
    }

    @JsonIgnore
    public Set<String> getAllItems() {
        Set<String> result = new HashSet<>();
        result.addAll(getItems());
        for (Subsystem subsystem : getChild()) {
            result.addAll(subsystem.getAllItems());
        }
        return result;
    }

    public Set<Subsystem> getChild() {
        return child;
    }

    @Override
    public String getListPresentation() {        
        return "Подсистемы";
    }    

    @Override
    public ObjectReader parse() throws IOException {
        ObjectReader objReader = super.parse();

        // Чтение подчиненых подсистем
        List<String> childXml = objReader.readChild("/MetaDataObject/Subsystem/ChildObjects/Subsystem");
        for (String item : childXml) {
            Path childPath = super.getFile().getParent()
                    .resolve(this.getShortName(super.getFile()))
                    .resolve("Subsystems")
                    .resolve(item + ".xml");

            Subsystem children = new Subsystem(childPath);
            children.parse();

            this.child.add(children);
        }

        // Чтение объектов, включенных в подсистему
        List<String> itemsXml = objReader.readChild("/MetaDataObject/Subsystem/Properties/Content/Item");
        for (String item : itemsXml) {
            this.items.add(item);
        }
        return objReader;
    }
}
