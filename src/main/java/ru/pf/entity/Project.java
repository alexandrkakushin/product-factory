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

    /**
     * Хранилище конфигурации 1С
     */
    @ManyToOne
    @JoinColumn(name="cr_id")
    private Cr cr;

    /**
     * Git-репозиторий
     */
    @ManyToOne
    @JoinColumn(name = "git_id")
    private Git git;

    /**
     * Каталог с файлами конфигурации
     */
    private String directory;

    /**
     * Источник исходных кодов
     */
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    public Project() {}

    @Override
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }
    
    public String getComment() {
    	return comment;
    }

    public SourceType getSourceType() {
        return sourceType;
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

    public String getDirectory() {
        return directory;
    }

    public enum SourceType {
        FILE_ZIP, FILE_CF, DIRECTORY, CR, GIT

//        @Override
//        public String toString() {
//            if (this == DIRECTORY) {
//                return "Каталог";
//
//            } else if (this == CR) {
//                return "Хранилище конфигурации";
//
//            } else if (this == GIT) {
//                return "GIT";
//
//            } else {
//                return super.toString();
//            }
//        }
    }
}
