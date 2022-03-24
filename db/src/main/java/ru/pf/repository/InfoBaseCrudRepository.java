package ru.pf.repository;

import ru.pf.entity.InfoBase;

/**
 * CRUD-репозиторий для работы с информационными базами данных
 * @author a.kakushin
 */
public interface InfoBaseCrudRepository extends PfCrudRepository<InfoBase> {

    @Override
    default InfoBase newInstance() {
        return new InfoBase();
    }
}
