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

    @ManyToOne
    @JoinColumn(name="cr_id")
    private Cr cr;

    public Project() {}

    public Long getId() {
        return id;
    }

    public Cr getCr() {
        return cr;
    }

    public void setCr(Cr cr) {
        this.cr = cr;
    }
}
