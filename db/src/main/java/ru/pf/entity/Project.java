package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;
import ru.pf.service.sourcecode.SourceCodeRepository;

import javax.persistence.*;

/**
 * Описание проекта
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "PROJECTS")
public class Project implements PfEntity<Project> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Наименование проекта
     */
    private String name;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Связь с классом "Конфигуратор", который будет использован для операций с хранилищем конфигурации
     */
    @ManyToOne
    @JoinColumn(name="designer_id")
    private Designer designer;

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
     * Имя расширения, используется в случае обновления из информационной базы
     * т.к. ИБ может иметь множество расширений, нужно явно указать с каким связан проект
     */
    @Column(name = "infobase_name_extension")
    private String nameExtension;

    /**
     * Связь с информационной базой, используется для режима обновления исходных кодов из информационной базы
     */
    @ManyToOne
    @JoinColumn(name = "infobase_id")
    private InfoBase infoBase;

    /**
     * Источник исходных кодов
     */
    @Enumerated(EnumType.STRING)
    private SourceCodeRepository.Types typeOfSourceCodeRepository;

    /**
     * Тип проекта
     */
    @Enumerated(EnumType.STRING)
    private Type type;

    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Тип проекта
     */
    public enum Type {
        /**
         * Конфигурация
         */
        CONF,

        /**
         * Расширение
         */
        EXTENSION
    }
}
