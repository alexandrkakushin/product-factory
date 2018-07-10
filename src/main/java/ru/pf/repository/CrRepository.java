package ru.pf.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.pf.entity.Cr;

/**
 * @author a.kakushin
 */
public interface CrRepository extends CrudRepository<Cr, Long> {

    Iterable<Cr> findAll(Sort sort);

}
