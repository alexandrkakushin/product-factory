package ru.pf.repository;

import ru.pf.entity.Os;

import java.util.Optional;

/**
 * Репозиторий "Операционные системы"
 * @author a.kakushin
 */
public interface OsRepository extends PfRepository<Os> {

    /**
     * Поиск по наименованию
     * @param name Наименование операционной системы
     * @return Optional(OS)
     */
    Optional<Os> findByName(String name);

    @Override
    default Os newInstance() {
        return new Os();
    }
}
