package ru.pf.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Описание полномочий для входа в систему
 * @author a.kakushin
 */
@Component
@ConfigurationProperties(prefix = "credentials")
@Setter
public class CredentialsProperties {

    /**
     * Пользователь "Администратор"
     */
    private Admin admin;

    @Setter
    public static class Admin {

        /**
         * Имя администратора
         */
        private String name;

        /**
         * Пароль администратора
         */
        private String password;

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
    }

    public Admin getAdmin() {
        return admin;
    }
}
