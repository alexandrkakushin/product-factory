package ru.pf.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.pf.entity.Project;

/**
 * @author a.kakushin
 */
public interface ProjectsRepository extends CrudRepository<Project, Long> {

    Iterable<Project> findAll(Sort sort);

}
