package ru.pf.metadata.type;

import lombok.Data;

import java.lang.String;

/**
 * @author a.kakushin
 */
@Data
public class Type {

    private String name;

    // todo: добавить чтение деталей типа данных
    private IType details;

    public Type(String name) {
        this.name = name;
    }

    public Type(String name, IType details) {
        this.name = name;
        this.details = details;
    }
}
