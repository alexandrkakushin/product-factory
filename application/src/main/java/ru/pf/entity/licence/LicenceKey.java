package ru.pf.entity.licence;

import lombok.Getter;
import lombok.Setter;
import ru.pf.entity.PfEntity;

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
    @Column(unique = true)
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

    /**
     * Размер прикрепленного файла ключа
     * @return Размер файла в Кб
     */
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
