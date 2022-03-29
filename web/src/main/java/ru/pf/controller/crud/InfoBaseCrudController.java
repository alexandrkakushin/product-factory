package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.InfoBase;
import ru.pf.repository.InfoBaseCrudRepository;
import ru.pf.repository.PfCrudRepository;

/**
 * Контроллер для реализации CRUD-операций с сущностью "Информационная база"
 * @author a.kakushin
 */
@Controller
@RequestMapping(InfoBaseCrudController.URL)
public class InfoBaseCrudController implements PfCrudController<InfoBase> {

    static final String URL = "development/infobases";

    @Autowired
    private InfoBaseCrudRepository infoBasesRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "infobases-item";
    }

    @Override
    public String getName() {
        return "Информационные базы";
    }

    @Override
    public PfCrudRepository<InfoBase> getRepository() {
        return this.infoBasesRepository;
    }
}
