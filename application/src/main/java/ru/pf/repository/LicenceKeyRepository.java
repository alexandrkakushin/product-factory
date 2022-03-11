package ru.pf.repository;

import ru.pf.entity.LicenceKey;

/**
 * Репозиторий "Ключи системы лицензирования конфигураций"
 * @author a.kakushin
 */
public interface LicenceKeyRepository extends PfRepository<LicenceKey> {

    @Override
    default LicenceKey newInstance() {
        return new LicenceKey();
    }
}
