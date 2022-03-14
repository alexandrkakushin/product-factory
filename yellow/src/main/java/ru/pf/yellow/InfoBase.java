package ru.pf.yellow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;

/**
 * Описание информационной базы 1C:Предприятие
 * @author a.kakushin
 */
@Getter
@Setter
@NoArgsConstructor
public class InfoBase {

    /**
     * Расположение файловой информационной базы
     */
    private Path path;

    /**
     * Логин
     */
    private String login;

    /**
     * Пароль
     */
    private String password;

    /**
     * Конструктор с указанием расположения информационной базы
     * @param path Расположение информационной базы
     */
    public InfoBase(Path path) {
        this.path = path;
    }

    /**
     * Конструктор с указанием расположения информационной базы, логина и пароля
     * @param path Распложение информационной базы
     * @param login Логин
     * @param password Пароль
     */
    public InfoBase(Path path, String login, String password) {
        this.path = path;
        this.login = login;
        this.password = password;
    }

    /**
     * Возвращает тип информационной базы
     * @return Булево, в случае файловой информационной базы
     */
    public boolean isFileBase() {
        return true;
    }

}
