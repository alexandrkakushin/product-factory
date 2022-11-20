package ru.pf.controller.crud.licence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.crud.PfCrudController;
import ru.pf.entity.licence.journal.JournalBuildSolution;
import ru.pf.repository.PfCrudRepository;
import ru.pf.repository.licence.JournalBuildSolutionCrudRepository;

/**
 * Контроллер для реализации чтения журнала защищенных решений
 * @author a.kakushin
 */
@Controller
@RequestMapping(JournalBuildSolutionCrudController.URL)
public class JournalBuildSolutionCrudController implements PfCrudController<JournalBuildSolution> {

    static final String URL = "licence/solution/journal";

    /**
     * Репозиторий для чтения/записи журнала сборок защищенных решений
     */
    @Autowired
    private JournalBuildSolutionCrudRepository repository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getName() {
        return "Журнал сборок";
    }

    @Override
    public String getTemplateItem() {
        return null;
    }

    @Override
    public String getTemplateList() {
        return getUrl() + "/" + "journal-build-solution-items";
    }

    @Override
    public Iterable<JournalBuildSolution> getItems() {
        return getRepository().findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public PfCrudRepository<JournalBuildSolution> getRepository() {
        return repository;
    }
}
