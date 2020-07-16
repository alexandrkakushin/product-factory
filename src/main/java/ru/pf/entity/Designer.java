package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * "Конфигураторы"
 * 
 * @author a.kakushin
 */
@Entity
@Table(name = "DESIGNERS")
@Data
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