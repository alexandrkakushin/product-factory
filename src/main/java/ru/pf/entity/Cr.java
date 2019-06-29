package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * CR - Configuration Repository
 * @author a.kakushin
 */
@Entity
@Table(name = "CR")
@Data
public class Cr implements PfEntity<Cr, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    private String tcp;
    private String http;

    private String login;
    private String password;

    public Cr() {}

    @Override
    public Long getId() {
        return this.id;
    }
}
