package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "GIT")
@Data
public class Git implements PfEntity<Git> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    private String fetchUrl;
    private String defaultBranch;
    
}
