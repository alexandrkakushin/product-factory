package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность "Сервер"
 * @author a.kakushin
 */
@Entity
@Table(name = "SERVERS")
@Data
public class Server implements PfEntity<Server, Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    public Server() {}

    @Override
    public Long getId() {
        return this.id;
    }
}
