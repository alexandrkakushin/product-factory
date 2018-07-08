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
public class Os {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    public Os() {}

    public Long getId() {
        return id;
    }
}
