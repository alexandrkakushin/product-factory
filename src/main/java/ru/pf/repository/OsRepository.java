package ru.pf.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.pf.entity.Os;

/**
 * Репозиторий "Операционные системы"
 * @author a.kakushin
 */
public interface OsRepository extends CrudRepository<Os, Long> {

    Iterable<Os> findAll(Sort sort);
}
