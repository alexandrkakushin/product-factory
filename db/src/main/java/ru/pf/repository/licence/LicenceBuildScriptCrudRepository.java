package ru.pf.repository.licence;

import ru.pf.entity.licence.LicenceBuildScript;
import ru.pf.repository.PfCrudRepository;

/**
 * Репозиторий для редактирования сценариев сборки защищенных модулей СЛК
 * @author a.kakushin
 */
public interface LicenceBuildScriptCrudRepository extends PfCrudRepository<LicenceBuildScript> {

    @Override
    default LicenceBuildScript newInstance() {
        return new LicenceBuildScript();
    }
}
