package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Git;
import ru.pf.repository.GitRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(GitCrudController.url)
public class GitCrudController implements PfCrudController<Git> {

    final static String url = "vcs/git";

    @Autowired
    private GitRepository gitRepository;

    @Override
    public String getUrl() {
        return url;
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
    public PfRepository<Git> getRepository() {
        return this.gitRepository;
    }
}
