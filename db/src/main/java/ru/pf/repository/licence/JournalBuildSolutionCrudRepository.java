package ru.pf.repository.licence;

import ru.pf.entity.licence.journal.JournalBuildSolution;
import ru.pf.repository.PfCrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий "Журнал формирования защищенных решений"
 * @author a.kakushin
 */
public interface JournalBuildSolutionCrudRepository extends PfCrudRepository<JournalBuildSolution> {

    /**
     * Поиск записи по идентификатору операции
     * @param uuid Уникальный идентификатор операции
     * @return Запись журнала
     */
    Optional<JournalBuildSolution> findByOperation(UUID uuid);

    @Override
    default JournalBuildSolution newInstance() {
        return new JournalBuildSolution();
    }
}
