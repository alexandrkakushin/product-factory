package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность "Служба сервера"
 * @author a.kakushin
 */
@Entity
@Table(name = "SERVICES")
@Data
public class Service implements PfEntity<Service, Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    public Service() {}

    @Override
    public Long getId() {
        return this.id;
    }
}
