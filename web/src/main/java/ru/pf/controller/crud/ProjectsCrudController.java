package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Project;
import ru.pf.repository.*;
import ru.pf.service.sourcecode.SourceCodeRepository;

/**
 * Контроллер для реализации CRUD-операций с проектами
 * @author a.kakushin
 */
@Controller
@RequestMapping(ProjectsCrudController.URL)
public class ProjectsCrudController implements PfCrudController<Project> {

    static final String URL = "development/projects";

    @Autowired
    private ProjectsCrudRepository projectsRepository;

    @Autowired
    private DesignerCrudRepository designerRepository;

    @Autowired
    private CrCrudRepository crRepository;

    @Autowired
    private GitCrudRepository gitRepository;

    /**
     * Репозиторий для получения информационных баз
     */
    @Autowired
    private InfoBaseCrudRepository infoBaseRepository;

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
    public PfCrudRepository<Project> getRepository() {
        return this.projectsRepository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("crList", crRepository.findAll(Sort.by("name")));
        model.addAttribute("gitList", gitRepository.findAll(Sort.by("name")));
        model.addAttribute("infoBaseList", infoBaseRepository.findAll(Sort.by("name")));

        model.addAttribute("designerList", designerRepository.findAll(Sort.by("name")));

        // Источники исходных кодов
        model.addAttribute("allSourceTypes", SourceCodeRepository.Types.values());

        // Типы проекта
        model.addAttribute("projectTypes", Project.Type.values());
    }

    @Override
    public String submit(@ModelAttribute Project entity) throws SubmitException {
        if (entity.getCr() != null && entity.getCr().getId() == null) {
            entity.setCr(null);
        }

        if (entity.getGit() != null && entity.getGit().getId() == null) {
            entity.setGit(null);
        }

        if (entity.getDesigner() != null && entity.getDesigner().getId() == null) {
            entity.setDesigner(null);
        }

        if (entity.getInfoBase() != null && entity.getInfoBase().getId() == null) {
            entity.setInfoBase(null);
        }

        return PfCrudController.super.submit(entity);
    }
}
