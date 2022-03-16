package ru.pf.repository.licence;

import ru.pf.entity.licence.Journal;
import ru.pf.repository.PfRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий "Журнал формирования защищенных обработок"
 * @author a.kakushin
 */
public interface JournalRepository extends PfRepository<Journal> {

    /**
     * Поиск записи по идентификатору операции
     * @param uuid Уникальный идентификатор операции
     * @return Запись журнала
     */
    Optional<Journal> findByOperation(UUID uuid);

    @Override
    default Journal newInstance() {
        return new Journal();
    }
}
