package ru.pf.repository;

import ru.pf.entity.Service;

/**
 * Репозиторий "Сервисы"
 * @author a.kakushin
 */
public interface ServicesCrudRepository extends PfCrudRepository<Service> {

    @Override
    default Service newInstance() {
        return new Service();
    }
}
