package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "PROJECTS")
@Data
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
