package ru.pf.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author a.kakushin
 */
@NoRepositoryBean
public interface PfRepository<T> extends CrudRepository<T, Long> {

    Iterable<T> findAll(Sort sort);

    T newInstance();

}
