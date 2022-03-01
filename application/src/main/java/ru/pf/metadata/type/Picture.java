package ru.pf.metadata.type;

import lombok.Data;
import ru.pf.metadata.reader.ObjectReader;
import ru.pf.metadata.reader.XmlReader;

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
        XmlReader xmlReader = objReader.getXmlReader();

        java.lang.String value = xmlReader.read(expression);
        if (!value.isEmpty()) {
            this.ref = xmlReader.read(expression + "/Ref");
            this.loadTransparent = xmlReader.readBool(expression + "/LoadTransparent");

            setStd(this.ref);
        }
    }
    
    public boolean isStd() {
		return isStd;
	}

	public java.lang.String getRef() {
		return ref;
	}

	public boolean isLoadTransparent() {
		return loadTransparent;
	}

	public void setStd(java.lang.String ref) {
        this.isStd = ref.startsWith("StdPicture.");
    }
}
