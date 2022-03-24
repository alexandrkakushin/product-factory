package ru.pf.repository;

import ru.pf.entity.Git;

/**
 * Репозиторий "GIT"
 * @author a.kakushin
 */
public interface GitCrudRepository extends PfCrudRepository<Git> {

    @Override
    default Git newInstance() {
        return new Git();
    }
}
