package ru.pf.metadata.object.common;

import lombok.Data;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.object.Catalog;
import ru.pf.metadata.reader.ObjectReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
public class CommandGroup extends AbstractObject<CommandGroup> {

    private Category category;
    private Representation representation;
    private String toolTip;

    // todo: поле Picture сделать отдельным типом данных

    public CommandGroup(Path path) {
        super(path);
    }

    @Override
    public void parse() throws IOException {
        Path fileXml = super.getFile().getParent().resolve(super.getFile());
        if (Files.exists(fileXml)) {
            ObjectReader objReader = new ObjectReader(fileXml);
            objReader.fillCommonField(this);

            String nodeProperties = "/MetaDataObject/" + getMetadataName() + "/Properties/";
            this.category = Category.valueByName(
                    objReader.read(nodeProperties + "Category"));

            this.representation = Representation.valueByName(
                    objReader.read(nodeProperties + "Representation"));
            this.toolTip = objReader.read(nodeProperties + "ToolTip");
        }
    }

    // todo: вынести в отдельный тип данных
    public enum Representation {
        PICTURE_AND_TEXT,
        TEXT,
        PICTURE,
        AUTO;

        public static Representation valueByName(String value) {
            if (value.equalsIgnoreCase("PictureAndText")) {
                return PICTURE_AND_TEXT;
            } else if (value.equalsIgnoreCase("Text")) {
                return TEXT;
            } else if (value.equalsIgnoreCase("Picture")) {
                return PICTURE;
            } else if (value.equalsIgnoreCase("Auto")) {
                return AUTO;
            }
            return null;
        }
    }

    public enum Category {
        NAVIGATION_PANEL,
        FORM_NAVIGATION_PANEL,
        ACTIONS_PANEL,
        FORM_COMMAND_BAR;

        public static Category valueByName(String value) {
            if (value.equalsIgnoreCase("NavigationPanel")) {
                return NAVIGATION_PANEL;
            } else if (value.equalsIgnoreCase("FormNavigationPanel")) {
                return FORM_NAVIGATION_PANEL;
            } else if (value.equalsIgnoreCase("ActionsPanel")) {
                return ACTIONS_PANEL;
            } else if (value.equalsIgnoreCase("FormCommandBar")) {
                return FORM_COMMAND_BAR;
            }
            return null;
        }
    }
}
