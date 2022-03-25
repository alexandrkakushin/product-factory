package ru.pf.yellow;

import lombok.Getter;
import lombok.Setter;

/**
 * Описание расширение конфигурации
 * @author a.kakushin
 */
@Getter
@Setter
public class Extension {

    /**
     * Имя расширения
     */
    private String name;

    /**
     * Конструктор с указанием имени расширения
     * @param name Имя расширения
     */
    public Extension(String name) {
        this.name = name;
    }
}
