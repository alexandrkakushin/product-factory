package ru.pf.metadata.type;

import lombok.Data;

import java.util.UUID;

/**
 * Описание предопределенного элемента
 * @author a.kakushin
 */
@Data
public class Predefined {

    private UUID id;
    private String name;
	private String code;
	private String description;
	private boolean isFolder;

	public Predefined() {}

    public Predefined(UUID id, String name, String code, String description, boolean isFolder) {
        this();

	    this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.isFolder = isFolder;
    }
}
