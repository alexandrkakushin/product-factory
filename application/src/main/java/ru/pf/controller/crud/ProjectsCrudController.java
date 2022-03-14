package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Project;
import ru.pf.repository.*;
import ru.pf.service.vcs.SourceCodeRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(ProjectsCrudController.URL)
public class ProjectsCrudController implements PfCrudController<Project> {

    static final String URL = "development/projects";

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private CrRepository crRepository;

    @Autowired
    private GitRepository gitRepository;

    @Override
    public String getUrl() {
        return URL;
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
    public PfRepository<Project> getRepository() {
        return this.projectsRepository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("crList", crRepository.findAll(Sort.by("id")));
        model.addAttribute("gitList", gitRepository.findAll(Sort.by("id")));

        model.addAttribute("designerList", designerRepository.findAll(Sort.by("id")));

        // Источники исходных кодов
        model.addAttribute("allSourceTypes", SourceCodeRepository.Types.values());
    }

    @PostMapping("/submit")
    @Override
    public String submit(@ModelAttribute Project entity) {
        if (entity.getCr() != null && entity.getCr().getId() == null) {
            entity.setCr(null);
        }

        if (entity.getGit() != null && entity.getGit().getId() == null) {
            entity.setGit(null);
        }

        if (entity.getDesigner() != null && entity.getDesigner().getId() == null) {
            entity.setDesigner(null);
        }

        Project saved = projectsRepository.save(entity);
        return "redirect:/" + getUrl() + "/" + saved.getId();
    }
}
