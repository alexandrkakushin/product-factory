package ru.pf.repository.auth;

import ru.pf.entity.auth.User;
import ru.pf.repository.PfCrudRepository;

import java.util.Optional;

/**
 * Репозиторий для операций с пользователями в базе данных
 * @author a.kakushin
 */
public interface UserCrudRepository extends PfCrudRepository<User> {

    /**
     * Поиск пользователя по имени
     * @param name Имя пользователя
     * @return User
     */
    Optional<User> findByName(String name);

    @Override
    default User newInstance() {
        return new User();
    }
}
