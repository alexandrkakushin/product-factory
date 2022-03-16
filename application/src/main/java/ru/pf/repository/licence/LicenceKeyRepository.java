package ru.pf.repository.licence;

import ru.pf.entity.licence.LicenceKey;
import ru.pf.repository.PfRepository;

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
