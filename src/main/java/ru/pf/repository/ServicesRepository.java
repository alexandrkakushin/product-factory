package ru.pf.repository;

import ru.pf.entity.Service;

/**
 * Репозиторий "Сервисы"
 * @author a.kakushin
 */
public interface ServicesRepository extends PfRepository<Service, Long> {

    @Override
    default Service newInstance() {
        return new Service();
    }
}
