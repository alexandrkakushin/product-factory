package ru.pf.repository;

import ru.pf.entity.Project;

import java.util.Optional;

/**
 * Репозиторий "Проекты"
 * @author a.kakushin
 */
public interface ProjectsRepository extends PfRepository<Project> {

    @Override
    default Project newInstance() {
        return new Project();
    }

    /**
     * Поиск по наименованию
     * @param name Наименование проекта
     * @return Optional(Project)
     */
    Optional<Project> findByName(String name);
}
