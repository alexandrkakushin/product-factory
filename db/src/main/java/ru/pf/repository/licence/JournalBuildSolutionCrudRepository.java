package ru.pf.repository.licence;

import ru.pf.entity.licence.journal.JournalBuildSolution;
import ru.pf.repository.PfCrudRepository;

/**
 * Репозиторий "Журнал формирования защищенных решений"
 * @author a.kakushin
 */
public interface JournalBuildSolutionCrudRepository extends PfCrudRepository<JournalBuildSolution> {
    @Override
    default JournalBuildSolution newInstance() {
        return new JournalBuildSolution();
    }
}
