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
public class Os implements PfEntity<Os, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    public Os() {}

    @Override
    public Long getId() {
        return this.id;
    }
}
