package ru.pf.controller.crud.licence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.crud.PfCrudController;
import ru.pf.controller.crud.SubmitException;
import ru.pf.entity.licence.LicenceBuildScript;
import ru.pf.repository.PfCrudRepository;
import ru.pf.repository.ProjectsCrudRepository;
import ru.pf.repository.licence.LicenceBuildScriptCrudRepository;
import ru.pf.repository.licence.LicenceKeyCrudRepository;

/**
 * Контроллер для реализации CRUD-операций с сценариями сборки защищенных модулей
 * @author a.kakushin
 */
@Controller
@RequestMapping(LicenceBuildScriptCrudController.URL)
public class LicenceBuildScriptCrudController implements PfCrudController<LicenceBuildScript> {

    static final String URL = "licence/buildscript";

    @Autowired
    private LicenceBuildScriptCrudRepository repository;

    @Autowired
    private ProjectsCrudRepository projectsRepository;

    @Autowired
    private LicenceKeyCrudRepository licenceKeyRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getName() {
        return "Сценарии сборки";
    }

    @Override
    public String getTemplateItem() {
        return "build-script-item";
    }

    @Override
    public PfCrudRepository<LicenceBuildScript> getRepository() {
        return this.repository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("projectList", projectsRepository.findAll(Sort.by("name")));
        model.addAttribute("licenceKeyList", licenceKeyRepository.findAll(Sort.by("name")));

        PfCrudController.super.addAttributesItem(model);
    }

    @Override
    public String submit(@ModelAttribute LicenceBuildScript entity) throws SubmitException {
        if (entity.getLicenceKey() != null && entity.getLicenceKey().getId() == null) {
            entity.setLicenceKey(null);
        }

        if (entity.getProject() != null && entity.getProject().getId() == null) {
            entity.setProject(null);
        }

        return PfCrudController.super.submit(entity);
    }
}
