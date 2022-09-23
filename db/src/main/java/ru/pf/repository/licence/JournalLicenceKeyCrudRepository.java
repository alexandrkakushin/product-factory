package ru.pf.repository.licence;

import ru.pf.entity.licence.journal.JournalLicenceKey;
import ru.pf.repository.PfCrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий "Журнал формирования защищенных обработок"
 * @author a.kakushin
 */
public interface JournalLicenceKeyCrudRepository extends PfCrudRepository<JournalLicenceKey> {

    /**
     * Поиск записи по идентификатору операции
     * @param uuid Уникальный идентификатор операции
     * @return Запись журнала
     */
    Optional<JournalLicenceKey> findByOperation(UUID uuid);

    @Override
    default JournalLicenceKey newInstance() {
        return new JournalLicenceKey();
    }
}
