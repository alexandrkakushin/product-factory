package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Информационная база
 * @author a.kakushin
 */
@Entity
@Getter
@Setter
@Table(name = "INFOBASES")
public class InfoBase implements PfEntity<InfoBase> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Наименование информационной базы
     */
    private String name;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Расположение файловой информационной базы
     */
    private String path;

    /**
     * Имя пользователя (администратора)
     */
    private String login;

    /**
     * Пароль
     */
    private String password;

    @Override
    public Long getId() {
        return this.id;
    }
}
