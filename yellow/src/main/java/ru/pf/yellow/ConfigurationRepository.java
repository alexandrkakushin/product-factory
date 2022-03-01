package ru.pf.yellow;

import lombok.Getter;

/**
 * Информация о хранилище конфигурации
 * @author a.kakushin
 */
@Getter
public class ConfigurationRepository {

    /**
     * Адрес подключения к хранилищу конфигурации
     */
    private String connectionString;

    /**
     * Имя пользователя
     */
    private String login;

    /**
     * Пароль
     */
    private String password;

    /**
     * Конструктор только со строкой подключения
     * @param connectionString Строка подключения с серверу хранилища конфигураций
     */
    public ConfigurationRepository(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * Конструктор с указанием строки подключения, имени пользователя и пароля
     * @param connectionString Строка подключения к серверу хранилища конфигураций
     * @param login Имя пользователя
     * @param password Пароль
     */
    public ConfigurationRepository(String connectionString, String login, String password) {
        this.connectionString = connectionString;
        this.login = login;
        this.password = password;
    }
}
