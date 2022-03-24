package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность "Служба сервера"
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "SERVICES")
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
