package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "PROJECTS")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    public Project() {}

    public Long getId() {
        return id;
    }
}
