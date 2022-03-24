package ru.pf.repository;

import ru.pf.entity.Property;

import java.util.Optional;

/**
 * Репозиторий для получения настроек
 * @author a.kakushin
 */
public interface PropertiesCrudRepository extends PfCrudRepository<Property> {

    @Override
    default Property newInstance() {
        return new Property();
    }

    Optional<Property> getByName(String name);

}
