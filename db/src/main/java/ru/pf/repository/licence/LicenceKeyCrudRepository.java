package ru.pf.repository.licence;

import ru.pf.entity.licence.LicenceKey;
import ru.pf.repository.PfCrudRepository;

/**
 * Репозиторий "Ключи системы лицензирования конфигураций"
 * @author a.kakushin
 */
public interface LicenceKeyCrudRepository extends PfCrudRepository<LicenceKey> {

    @Override
    default LicenceKey newInstance() {
        return new LicenceKey();
    }
}
