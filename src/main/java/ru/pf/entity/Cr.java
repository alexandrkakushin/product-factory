package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс CR - Configuration Repository (Хранилище конфигурации)
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "CR")
public class Cr implements PfEntity<Cr> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Название
     */
    private String name;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Адрес для подключения к серверу хранилища конфигураций
     */
    private String address;

    /**
     * Логин
     */
    private String login;

    /**
     * Пароль
     */
    private String password;

    /**
     * Связь с классом "Конфигуратор", который будет использован для операций с хранилищем конфигурации
     */
    @ManyToOne
    @JoinColumn(name="designer_id")
    private Designer designer;

    /**
     * Конструктор по умолчанию
     */
    public Cr() {
        // Конструктор по умолчанию
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
