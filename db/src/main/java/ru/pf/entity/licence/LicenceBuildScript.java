package ru.pf.entity.licence;

import lombok.Getter;
import lombok.Setter;
import ru.pf.entity.InfoBase;
import ru.pf.entity.PfEntity;
import ru.pf.entity.Project;

import javax.persistence.*;
import java.util.UUID;

/**
 * Сценарии сборки защищенных модулей СЛК
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "LICENCE_BUILD_SCRIPT")
public class LicenceBuildScript implements PfEntity<LicenceBuildScript> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Имя сценария
     */
    private String name;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Связь с проектом, необходима для получения списка общих модулей, а также доступа к конфигуратору
     */
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    /**
     * Связь с ключом системы лицензирования
     */
    @ManyToOne
    @JoinColumn(name = "licence_key_id")
    private LicenceKey licenceKey;

    /**
     * Информационная база для построения
     */
    @ManyToOne
    @JoinColumn(name = "info_base_id")
    private InfoBase infoBase;

    /**
     * Общий модуль, который должен быть изменен в процессе сборки
     * Подразумевается, что в этом ОМ размещены вызовы обработки
     */
    private UUID commonModule;

    /**
     * Текст, который будет размещен в общем модуле.
     * Необходимо заменить вызов обработки на защещенный СЛК-модуль
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String targetText;

    /**
     * Обработка, на основе которой будет создан защищенный модуль
     * Данная обработка должна быть удалена
     */
    private UUID dataProcessor;

    /**
     * Макет, в который будет записан защищенный общий модуль
     */
    private UUID template;

    @Override
    public Long getId() {
        return this.id;
    }
}