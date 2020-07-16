package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Сущность "Служба сервера"
 * @author a.kakushin
 */
@Entity
@Table(name = "SERVICES")
@Data
public class Service implements PfEntity<Service> {

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
