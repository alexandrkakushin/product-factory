package ru.pf.metadata.object;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;
import ru.pf.metadata.annotation.*;
import ru.pf.metadata.reader.ObjectReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Catalog extends AbstractMetadataObject {

    // Иерархия

    /**
     * Иерархический
     */
    private boolean hierarchical;

    /**
     * Вид иерархиии
     */
    private HierarchyType hierarchyType;

    /**
     * Ограничение количества уровней иерархии
     */
    private boolean limitLevelCount;

    /**
     * Количество уровней иерархии
     */
    private int levelCount;

    /**
     * Размещать группы сверху
     */
    private boolean foldersOnTop;

    // Владельцы

    /**
     * Список владельцев справочника
     */
    @Owners
    private Set<MetadataObject> owners;

    /**
     * Использование подчинения
     */
    private SubordinationUse subordinationUse;

    // Данные

    /**
     * Длина кода
     */
    private int codeLength;

    /**
     * Длина наименования
     */
    private int descriptionLength;

    /**
     * Тип кода
     */
    private CodeType codeType;

    /**
     * Основное представление
     */
    private DefaultPresentation defaultPresentation;


    // todo: <CodeAllowedLength>,


    // Нумерация

    /**
     * Контроль уникальности
     */
    private boolean checkUnique;

    /**
     * Автонумерация
     */
    private boolean autoNumbering;

    /**
     * Серии кодов
     */
    private CodeSeries codeSeries;

//
//	// todo: <Characteristics/>
//    // todo: <PredefinedDataUpdate>Auto</PredefinedDataUpdate>
//    // todo: <EditType>InDialog</EditType>

    private Set<Attribute> standardAttributes;
    private Set<Attribute> attributes;

    // Формы

    /**
     * Список форм справочника
     */
    @Forms
    private Set<Form> forms;

    // Поле ввода

    /**
     * Быстрый выбор
     */
    private boolean quickChoice;

    /**
     * Способ выбора
     * Задает приоритетный способ выбора для поля ввода.
     */
    private ChoiceMode choiceMode;

    // Команды

    /**
     * Использовать стандартные команды
     */
    private boolean useStandardCommands;


    @ObjectModule
    private Module objectModule;

    @ManagerModule
    private Module managerModule;

    @Predefined
    private Set<ru.pf.metadata.type.Predefined> predefined;

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

        String nodeRoot = "/MetaDataObject/" + getXmlName();
        
        String nodeProperties = nodeRoot + "/Properties/";
        this.hierarchical  = objReader.readBool(nodeProperties + "Hierarchical");
        this.hierarchyType = HierarchyType.valueByName(
                objReader.read(nodeProperties + "HierarchyType"));

        this.limitLevelCount = objReader.readBool(nodeProperties + "LimitLevelCount");
        this.levelCount = objReader.readInt(nodeProperties + "LevelCount");
        this.foldersOnTop = objReader.readBool(nodeProperties + "FoldersOnTop");
        this.useStandardCommands = objReader.readBool(nodeProperties + "UseStandardCommands");

        // owners

        this.subordinationUse = SubordinationUse.valueByName(
                objReader.read(nodeProperties + "SubordinationUse"));

        this.codeLength = objReader.readInt(nodeProperties + "CodeLength");
        this.descriptionLength = objReader.readInt(nodeProperties + "DescriptionLength");

        // Данные
        this.codeType = CodeType.valueByName(
                objReader.read(nodeProperties + "CodeType"));

        this.defaultPresentation = DefaultPresentation.valueByName(
            objReader.read(nodeProperties + "DefaultPresentation"));

        this.codeSeries = CodeSeries.valueByName(
                objReader.read(nodeProperties + "CodeSeries"));
        this.checkUnique = objReader.readBool(nodeProperties + "CheckUnique");
        this.autoNumbering = objReader.readBool(nodeProperties +  "Autonumbering");

        // Поле ввода
        this.quickChoice = objReader.readBool(nodeProperties + "QuickChoice");
        this.choiceMode = ChoiceMode.valueByName(
            objReader.read(nodeProperties + "ChoiceMode"));

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

    /**
     * Основное представление в виде кода или наименования
     */
    public enum DefaultPresentation {
        AS_DESCRIPTION,
        AS_CODE;

        public static DefaultPresentation valueByName(String value) {
            if (value.equalsIgnoreCase("AsCode")) {
                return AS_CODE;
            } else if (value.equalsIgnoreCase("AsDescription")) {
                return AS_DESCRIPTION;
            }
            return null;
        }
    }

    /**
     * Приоритетный способ выбора для поля ввода
     */
    public enum ChoiceMode {
        BOTH_WAYS,
        FROM_FORM,
        QUICK_CHOICE;

        public static ChoiceMode valueByName(String value) {
            if (value.equalsIgnoreCase("BothWays")) {
                return BOTH_WAYS;
            } else if (value.equalsIgnoreCase("FromForm")) {
                return FROM_FORM;
            } else if (value.equalsIgnoreCase("QuickChoice")) {
                return QUICK_CHOICE;
            }
            return null;
        }
    }
}
