package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность "Операционная система"
 * @author a.kakushin
 */
@Entity
@Table(name = "OS")
@Data
public class Os implements PfEntity<Os> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Название операционной системы
     */
    private String name;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Конструктор по умолчанию
     */
    public Os() {
    }

    /**
     * Конструктор с указанием названия ОС
     * @param name Название ОС
     */
    public Os(String name) {
        this();
        this.name = name;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
