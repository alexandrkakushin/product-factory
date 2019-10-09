package ru.pf.metadata.object;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.reader.ModuleReader;
import ru.pf.metadata.reader.ObjectReader;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Enum extends AbstractMetadataObject {

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

    private Module managerModule;

    public Enum(Path path) {
        super(path);
    }

    @Override
    public ObjectReader parse() throws IOException {
        ObjectReader objReader = super.parse();

        String nodeRoot = "/MetaDataObject/" + getXmlName();

        this.useStandardCommands  = objReader.readBool(nodeRoot + "Properties/UseStandardCommands");

        // Enum values
        this.values = new HashSet<>();
        List<String> enumsUuid = objReader.readChild(nodeRoot + "/ChildObjects/EnumValue/@uuid");       
        enumsUuid.forEach(
            uuid -> {
                this.values.add(
                    new EnumValue(UUID.fromString(uuid), 
                        objReader.read(nodeRoot + "/ChildObjects/EnumValue[@uuid='" + uuid + "']/Properties/Name"),
                        objReader.read(nodeRoot + "/ChildObjects/EnumValue[@uuid='" + uuid + "']/Properties/Synonym/item/content"), 
                        objReader.read(nodeRoot + "/ChildObjects/EnumValue[@uuid='" + uuid + "']/Properties/Comment")
                    )
                );
            }
        );

        Path pathExt = super.getFile()
                .getParent()
                .resolve(this.getShortName(super.getFile()))
                .resolve("Ext");

        if (Files.exists(pathExt)) {
            Path fileManagerModule = pathExt.resolve("ManagerModule.bsl");
            if (Files.exists(fileManagerModule)) {
                this.managerModule = ModuleReader.read(fileManagerModule, Module.Type.MANAGER_MODULE);
            }
        }

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