package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "PROPERTIES")
public class Property implements PfEntity<Property> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    private String comment;

    @Override
    public Long getId() {
        return this.id;
    }
}
