package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * "Конфигураторы"
 * 
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "DESIGNERS")
public class Designer implements PfEntity<Designer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String host;
    private String version;
    private String path;

    private String comment;

    @Override
    public Long getId() {
        return this.id;
    }
}