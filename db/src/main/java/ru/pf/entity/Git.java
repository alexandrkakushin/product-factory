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
@Table(name = "GIT")
public class Git implements PfEntity<Git> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    private String fetchUrl;
    private String defaultBranch;

    @Override
    public Long getId() {
        return this.id;
    }
}
