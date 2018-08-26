package ru.pf.repository;

import ru.pf.entity.Property;

import java.util.Optional;

/**
 * Репозиторий для получения настроек
 * @author a.kakushin
 */
public interface PropertiesRepository extends PfRepository<Property, Long> {

    @Override
    default Property newInstance() {
        return new Property();
    }

    Optional<Property> getByName(String name);

}
