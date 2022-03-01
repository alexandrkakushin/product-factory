package ru.pf.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Роль пользователя
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

    /**
     * Идентификатор роли
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Имя роли
     */
    private String name;

    /**
     * Пользователи роли
     */
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    /**
     * Конструктор по умолчанию
     */
    public Role() {
    }

    /**
     * Конструктор создания роли по идентификатору и наименованию
     * @param name Имя роли
     */
    public Role(String name) {
        this();
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}