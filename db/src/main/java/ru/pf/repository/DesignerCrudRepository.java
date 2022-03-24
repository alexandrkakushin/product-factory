package ru.pf.repository;

import ru.pf.entity.Designer;

/**
 * Репозиторий "Хранилища конфигураций"
 * @author a.kakushin
 */
public interface DesignerCrudRepository extends PfCrudRepository<Designer> {

    @Override
    default Designer newInstance() {
        return new Designer();
    }
}
