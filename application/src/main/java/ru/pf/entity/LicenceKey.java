package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс "Ключ системы лицензирования конфигураций"
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "LICENCE_KEY")
public class LicenceKey implements PfEntity<LicenceKey> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Название ключа (серия)
     */
    private String name;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Имя прикрепленного файла
     */
    private String fileName;

    /**
     * Прикрепленный файл ключа
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] attachedFile;

    public int getFileSize() {
        if (this.getAttachedFile() != null) {
            return this.getAttachedFile().length / 1024;
        }
        return 0;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
