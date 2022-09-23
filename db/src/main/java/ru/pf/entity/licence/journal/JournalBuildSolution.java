package ru.pf.entity.licence.journal;

import lombok.Getter;
import lombok.Setter;
import ru.pf.entity.PfEntity;
import ru.pf.entity.licence.LicenceBuildScript;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Журнал создания защищенного решения
 * Используется для истории и дальнейшего скачивания файла
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "JOURNAL_BUILD_SOLUTION")
public class JournalBuildSolution implements PfEntity<JournalBuildSolution> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Сценарий сборки защищенного решения
     */
    @ManyToOne
    @JoinColumn(name = "licence_build_script_id")
    private LicenceBuildScript licenceBuildScript;

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
     * Этап сборки
     */
    private String stage;
    
    /**
     * Имя сгенерированного файла
     */
    private String fileNameForDownload;

    /**
     * Размер файла
     */
    private long size;
    
    /**
     * Текст ошибки построения
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String textError;

    /**
     * Признак успешного построения
     */
    private boolean isSuccessful;
}
