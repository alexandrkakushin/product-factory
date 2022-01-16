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
@Table(name = "INFOBASES")
public class InfoBase implements PfEntity<InfoBase> {

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
