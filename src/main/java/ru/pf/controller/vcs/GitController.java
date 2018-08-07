package ru.pf.controller.vcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.PfController;
import ru.pf.entity.Git;
import ru.pf.repository.GitRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(GitController.url)
public class GitController implements PfController<Git, Long> {

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
    public PfRepository<Git, Long> getRepository() {
        return this.gitRepository;
    }
}
