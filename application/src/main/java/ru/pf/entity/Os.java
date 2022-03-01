package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс "Операционная система"
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "OS")
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

    /**
     * Конструктор с указанием идентификатора, названия и комментария
     * @param id Идентификатор записи
     * @param name Название ОС
     * @param comment Комментарий
     */
    public Os(Long id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
