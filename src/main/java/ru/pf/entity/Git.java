package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "GIT")
@Data
public class Git implements PfEntity<Git, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    @Override
    public Long getId() {
        return this.id;
    }
}
