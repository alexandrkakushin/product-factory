package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "PROJECTS")
@Data
public class Project implements PfEntity<Project, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    @ManyToOne
    @JoinColumn(name="cr_id")
    private Cr cr;

    @ManyToOne
    @JoinColumn(name = "git_id")
    private Git git;

    public Project() {}

    @Override
    public Long getId() {
        return this.id;
    }

    public Cr getCr() {
        return cr;
    }

    public void setCr(Cr cr) {
        this.cr = cr;
    }

    public Git getGit() {
        return git;
    }

    public void setGit(Git git) {
        this.git = git;
    }
}
