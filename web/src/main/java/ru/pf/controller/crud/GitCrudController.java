package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Git;
import ru.pf.repository.GitCrudRepository;
import ru.pf.repository.PfCrudRepository;

/**
 * Контроллер для реализации CRUD-операций с сущностью "Git-репозиторий"
 * @author a.kakushin
 */
@Controller
@RequestMapping(GitCrudController.URL)
public class GitCrudController implements PfCrudController<Git> {

    static final String URL = "vcs/git";

    @Autowired
    private GitCrudRepository gitRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "git-item";
    }

    @Override
    public String getName() {
        return "GIT-репозитории";
    }

    @Override
    public PfCrudRepository<Git> getRepository() {
        return this.gitRepository;
    }
}
