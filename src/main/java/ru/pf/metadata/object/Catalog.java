package ru.pf.metadata.object;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.reader.ModuleReader;
import ru.pf.metadata.reader.ObjectReader;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Catalog extends AbstractMetadataObject {

    private boolean hierarchical;

    private HierarchyType hierarchyType;
    private boolean limitLevelCount;
    private int levelCount;
    private boolean foldersOnTop;
    private boolean useStandardCommands;

    // todo: <Owners/>,
    private SubordinationUse subordinationUse;
    private int codeLength;
    private int descriptionLength;
    private CodeType codeType;

    // todo: <CodeAllowedLength>,
    private CodeSeries codeSeries;
//
	private boolean checkUnique;
	private boolean autonumbering;
//    // todo: <DefaultPresentation>AsDescription</DefaultPresentation>
//
//	// todo: <Characteristics/>
//    // todo: <PredefinedDataUpdate>Auto</PredefinedDataUpdate>
//    // todo: <EditType>InDialog</EditType>
    private boolean quickChoice;

    // todo: <ChoiceMode>BothWays</ChoiceMode>

    private Set<Attribute> standardAttributes;
    private Set<Attribute> attributes;

    @Forms
    private Set<Form> forms;

    private Module managerModule;
    private Module objectModule;

    public Catalog(Path path) {
        super(path);
    }

    @Override
    public String getListPresentation() {
        return "Справочники";
    }

    @Override
    public ObjectReader parse() throws IOException {
        ObjectReader objReader = super.parse();

        String nodeProperties = "/MetaDataObject/" + getXmlName() + "/Properties/";
        String nodeRoot = "/MetaDataObject/" + getXmlName();
        
        this.hierarchical  = objReader.readBool(nodeProperties + "Hierarchical");
        this.hierarchyType = HierarchyType.valueByName(
                objReader.read(nodeProperties + "HierarchyType"));

        this.limitLevelCount = objReader.readBool(nodeProperties + "LimitLevelCount");
        this.levelCount = objReader.readInt(nodeProperties + "LevelCount");
        this.foldersOnTop = objReader.readBool(nodeProperties + "FoldersOnTop");
        this.useStandardCommands = objReader.readBool(nodeProperties + "UseStandardCommands");

        this.subordinationUse = SubordinationUse.valueByName(
                objReader.read(nodeProperties + "SubordinationUse"));

        this.codeLength = objReader.readInt(nodeProperties + "CodeLength");
        this.descriptionLength = objReader.readInt(nodeProperties + "DescriptionLength");

        this.codeType = CodeType.valueByName(
                objReader.read(nodeProperties + "CodeType"));

        this.codeSeries = CodeSeries.valueByName(
                objReader.read(nodeProperties + "CodeSeries"));
        this.checkUnique = objReader.readBool(nodeProperties + "CheckUnique");
        this.autonumbering = objReader.readBool(nodeProperties +  "Autonumbering");
        this.quickChoice = objReader.readBool(nodeProperties + "QuickChoice");
        
        Path pathExt = super.getFile()
                .getParent()
                .resolve(this.getShortName(super.getFile()))
                .resolve("Ext");

        if (Files.exists(pathExt)) {
            Path fileManagerModule = pathExt.resolve("ManagerModule.bsl");
            if (Files.exists(fileManagerModule)) {
                this.managerModule = ModuleReader.read(fileManagerModule, Module.Type.MANAGER_MODULE);
            }

            Path fileObjectModule = pathExt.resolve("ObjectModule.bsl");
            if (Files.exists(fileObjectModule)) {
                this.objectModule = ModuleReader.read(fileObjectModule, Module.Type.OBJECT_MODULE);
            }
        }

        return objReader;
    }

    public enum HierarchyType {
        HIERARCHY_OF_ITEMS,
        HIERARCHY_FOLDERS_AND_ITEMS;

        public static HierarchyType valueByName(String value) {
            if (value.equalsIgnoreCase("HierarchyOfItems")) {
                return HIERARCHY_OF_ITEMS;
            } else if (value.equalsIgnoreCase("HierarchyFoldersAndItems")) {
                return HIERARCHY_FOLDERS_AND_ITEMS;
            }
            return null;
        }
    }

    public enum SubordinationUse {
        TO_ITEMS,
        TO_FOLDERS,
        TO_FOLDERS_AND_ITEMS;

        public static SubordinationUse valueByName(String value) {
            if (value.equalsIgnoreCase("ToItems")) {
                return TO_ITEMS;
            } else if (value.equalsIgnoreCase("ToFolders")) {
                return TO_FOLDERS;
            } else if (value.equalsIgnoreCase("ToFoldersAndItems")) {
                return TO_FOLDERS_AND_ITEMS;
            }
            return null;
        }
    }

    public enum CodeType {
        STRING,
        NUMBER;

        public static CodeType valueByName(String value) {
            if (value.equalsIgnoreCase("String")) {
                return STRING;
            } else if (value.equalsIgnoreCase("Number")) {
                return NUMBER;
            }
            return null;
        }
    }

    public enum CodeSeries {
        WITHIN_OWNER_SUBORDINATION,
        WITHIN_SUBORDINATION,
        WHOLE_CATALOG;

        public static CodeSeries valueByName(String value) {
            if (value.equalsIgnoreCase("WithinOwnerSubordination")) {
                return WITHIN_OWNER_SUBORDINATION;
            } else if (value.equalsIgnoreCase("WithinSubordination")) {
                return WITHIN_SUBORDINATION;
            } else if (value.equalsIgnoreCase("WholeCatalog")) {
                return WHOLE_CATALOG;
            }
            return null;
        }
    }
}
