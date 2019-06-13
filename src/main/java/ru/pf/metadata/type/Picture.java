package ru.pf.metadata.type;

import lombok.Data;
import ru.pf.metadata.reader.ObjectReader;

/**
 * Тип данных "Картинка"
 * @author a.kakushin
 */
@Data
public class Picture {

    /**
     * Признак стандартной картинки
     */
    private boolean isStd;

    /**
     * Ссылка, может быть как ссылкой на стандартную картинку, так и на общую картинку (CommonPicture)
     */
    private java.lang.String ref;

    /**
     * Признак установки прозрачности
     */
    private boolean loadTransparent;

    public Picture(java.lang.String ref, boolean loadTransparent) {
        this.ref = ref;
        this.loadTransparent = loadTransparent;

        setStd(this.ref);
    }

    public Picture(ObjectReader objReader, java.lang.String expression) {
        java.lang.String value = objReader.read(expression);
        if (!value.isEmpty()) {
            this.ref = objReader.read(expression + "/Ref");
            this.loadTransparent = objReader.readBool(expression + "/LoadTransparent");

            setStd(this.ref);
        }
    }

    public void setStd(java.lang.String ref) {
        this.isStd = ref.startsWith("StdPicture.");
    }
}
