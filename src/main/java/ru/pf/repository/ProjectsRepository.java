package ru.pf.repository;

import ru.pf.entity.Project;

/**
 * Репозиторий "Проекты"
 * @author a.kakushin
 */
public interface ProjectsRepository extends PfRepository<Project, Long> {

    @Override
    default Project newInstance() {
        return new Project();
    }
}
