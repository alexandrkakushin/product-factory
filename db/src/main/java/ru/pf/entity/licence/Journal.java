package ru.pf.entity.licence;

import lombok.Getter;
import lombok.Setter;
import ru.pf.entity.PfEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Журнал создания защищенных обработок (модулей) СЛК
 * Используется в API для хранения истории и дальнейшего скачивания
 */
@Getter
@Setter
@Entity
@Table(name = "JOURNAL_LICENCE_KEY")
public class Journal implements PfEntity<Journal> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Дата создания записи
     */
    private Date created;

    /**
     * Идентификатор операции
     */
    @Column(unique = true, nullable = false)
    private UUID operation;

    /**
     * Полное имя файла обработки
     */
    private String dataProcessing;

    /**
     * Полное имя файла защищенного модуля
     */
    private String protectedModule;

    /**
     * Имя сгенерированного файла
     */
    private String fileNameForDownload;

    /**
     * Размер файла
     */
    private long size;

    /**
     * Конструктор по умолчанию
     */
    public Journal() {
        this.created = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }
}
