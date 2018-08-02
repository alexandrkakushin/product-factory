package ru.pf.repository;

import ru.pf.entity.Server;

/**
 * Репозиторий "Серверы"
 * @author a.kakushin
 */
public interface ServersRepository extends PfRepository<Server, Long> {

    @Override
    default Server newInstance() {
        return new Server();
    }
}
