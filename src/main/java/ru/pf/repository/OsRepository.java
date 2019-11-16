package ru.pf.repository;

import ru.pf.entity.Os;

/**
 * Репозиторий "Операционные системы"
 * @author a.kakushin
 */
public interface OsRepository extends PfRepository<Os> {

    @Override
    default Os newInstance() {
        return new Os();
    }
}
