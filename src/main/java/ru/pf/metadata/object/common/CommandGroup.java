package ru.pf.metadata.object.common;

import java.io.IOException;
import java.nio.file.Path;

import lombok.Data;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.type.Picture;

/**
 * @author a.kakushin
 */
@Data
public class CommandGroup extends AbstractMetadataObject {

    private Category category;
    private Representation representation;
    private String toolTip;
    private Picture picture;

    public CommandGroup(Path path) {
        super(path);
    }

    @Override
    public ObjectReader parse() throws IOException {
        ObjectReader objReader = super.parse();

        String nodeProperties = "/MetaDataObject/" + getXmlName() + "/Properties/";
        this.category = Category.valueByName(
                objReader.read(nodeProperties + "Category"));

        this.representation = Representation.valueByName(
                objReader.read(nodeProperties + "Representation"));
        this.toolTip = objReader.read(nodeProperties + "ToolTip");
        this.picture = new Picture(objReader, nodeProperties + "Picture");

        return objReader;
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
