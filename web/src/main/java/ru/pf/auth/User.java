package ru.pf.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.pf.entity.PfEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Пользователь системы
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User implements UserDetails, PfEntity<User> {

    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Имя пользователя (логин)
     */
    private String name;

    /**
     * Пароль
     */
    private String password;

    /**
     * Подтвержденный пароль
     */
    @Transient
    private String passwordConfirm;

    /**
     * Роли пользователя
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    /**
     * Комментарий
     */
    private String comment;

    /**
     * Конструктор по умолчанию
     */
    public User() {
    }

    /**
     * Констуктор для создания пользователя
     * @param name Имя пользователя
     * @param password Пароль
     * @param roles Роли пользователя
     */
    public User(String name, String password, Set<Role> roles) {
        this();
        this.name = name;
        setPassword(password);
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
