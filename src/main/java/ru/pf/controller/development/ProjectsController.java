package ru.pf.controller.development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.controller.PfController;
import ru.pf.entity.Project;
import ru.pf.repository.CrRepository;
import ru.pf.repository.GitRepository;
import ru.pf.repository.PfRepository;
import ru.pf.repository.ProjectsRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(ProjectsController.url)
public class ProjectsController implements PfController<Project, Long> {

    final static String url = "development/projects";

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private CrRepository crRepository;

    @Autowired
    private GitRepository gitRepository;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getTemplateItem() {
        return "project-item";
    }

    @Override
    public String getName() {
        return "Проекты";
    }

    @Override
    public PfRepository<Project, Long> getRepository() {
        return this.projectsRepository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("crList", crRepository.findAll(Sort.by("id")));
        model.addAttribute("gitList", gitRepository.findAll(Sort.by("id")));

        // Источники исходных кодов
        model.addAttribute("allSourceTypes", Project.SourceType.values());
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Project entity) {
        if (entity.getCr() != null) {
            if (entity.getCr().getId() == null) {
                entity.setCr(null);
            }
        }

        if (entity.getGit() != null) {
            if (entity.getGit().getId() == null) {
                entity.setGit(null);
            }
        }

        Project saved = projectsRepository.save(entity);
        return "redirect:/" + url + "/" + saved.getId();
    }

}
