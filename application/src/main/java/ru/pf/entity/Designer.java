package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * "Конфигуратор, толстый клиент 1С:Предприятие"
 * 
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "DESIGNERS")
public class Designer implements PfEntity<Designer> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Наименование
     */
    private String name;

    /**
     * Версия
     */
    private String version;

    /**
     * Путь к исполняемому файлу
     */
    private String path;

    /**
     * Комментарий
     */
    private String comment;

    @Override
    public Long getId() {
        return this.id;
    }
}