package ru.pf.metadata.object;

import lombok.Data;
import org.aspectj.apache.bcel.classfile.Code;
import ru.pf.metadata.Module;
import ru.pf.metadata.reader.ModuleReader;
import ru.pf.metadata.reader.ObjectReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Data
public class Catalog extends AbstractObject<Catalog> {

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

    private Set<Attribute> attributes;

    private Module managerModule;
    private Module objectModule;

    public Catalog(Path path) {
        super(path);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void parse() throws IOException {
        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);

            String nodeProperties = "/MetaDataObject/" + getMetadataName() + "/Properties/";
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
        }

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
