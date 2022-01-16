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
@Table(name = "PROJECTS")
public class Project implements PfEntity<Project> {

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

    @Override
    public Long getId() {
        return this.id;
    }

    public enum SourceType {
        FILE_ZIP, FILE_CF, DIRECTORY, CR, GIT
    }
}
