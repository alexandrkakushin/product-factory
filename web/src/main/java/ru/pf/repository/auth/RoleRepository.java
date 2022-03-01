package ru.pf.repository.auth;

import ru.pf.auth.Role;
import ru.pf.repository.PfRepository;

import java.util.Optional;

/**
 * Репозиторий для операций с ролями в базе данных
 * @author a.kakushin
 */
public interface RoleRepository extends PfRepository<Role> {

    /**
     * Поиск по наименованию
     * @param name Наименование операционной системы
     * @return Optional(Role)
     */
    Optional<Role> findByName(String name);

    @Override
    default Role newInstance() {
        return new Role();
    }
}
