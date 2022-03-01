package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.metadata.reader.XmlReader;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Enum extends MetadataObject {

    private boolean useStandardCommands;

    // TODO: чтение основных свойств
    // <Characteristics/>
    // <QuickChoice>true</QuickChoice>
    // <ChoiceMode>BothWays</ChoiceMode>
    // <DefaultListForm/>
    // <DefaultChoiceForm/>
    // <AuxiliaryListForm/>
    // <AuxiliaryChoiceForm/>
    // <ListPresentation/>
    // <ExtendedListPresentation/>
    // <Explanation/>
    // <ChoiceHistoryOnInput>Auto</ChoiceHistoryOnInput>

    private Set<EnumValue> values;

    @Forms
    private Set<Form> forms;

    @ManagerModule
    private Module managerModule;

    public Enum(Path path) {
        super(path);
    }

    @Override
    public ObjectReader parse() throws ReaderException {
        ObjectReader objReader = super.parse();
        String nodeRoot = "/MetaDataObject/" + getXmlName();
        XmlReader xmlReader = objReader.getXmlReader();

        this.useStandardCommands  = xmlReader.readBool(nodeRoot + "Properties/UseStandardCommands");

        // Enum values
        this.values = new HashSet<>();
        List<String> enumsUuid = xmlReader.readChild(nodeRoot + "/ChildObjects/EnumValue/@uuid");
        enumsUuid.forEach(
            uuid -> {
                String propertiesNode = nodeRoot + "/ChildObjects/EnumValue[@uuid='" + uuid + "']/Properties";
                this.values.add(
                    new EnumValue(UUID.fromString(uuid),
                            xmlReader.read(propertiesNode + "/Name"),
                            xmlReader.read(propertiesNode + "/Synonym/item/content"),
                            xmlReader.read(propertiesNode + "/Comment")
                    )
                );
            }
        );

        return objReader;
    }

    @Override
    public String getListPresentation() {
        return "Перечисления";
    }    

    @Data
    public class EnumValue {

        private UUID uuid;
        private String name;
        private String synonym;
        private String comment;

        public EnumValue(UUID uuid, String name, String synonym, String comment) {
            this.uuid = uuid;
            this.name = name;
            this.synonym = synonym;
            this.comment = comment;
        }
    }
}