package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * CR - Configuration Repository
 * @author a.kakushin
 */
@Entity
@Table(name = "CR")
@Data
public class Cr implements PfEntity<Cr> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    private String address;

    private String login;
    private String password;

    @ManyToOne
    @JoinColumn(name="designer_id")
    private Designer designer;

    @Override
    public Long getId() {
        return this.id;
    }
}
