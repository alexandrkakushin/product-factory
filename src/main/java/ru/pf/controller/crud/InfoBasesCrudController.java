package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.InfoBase;
import ru.pf.repository.InfoBasesRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(InfoBasesCrudController.URL)
public class InfoBasesCrudController implements PfCrudController<InfoBase> {

    static final String URL = "development/infobases";

    @Autowired
    private InfoBasesRepository infoBasesRepository;

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
    public PfRepository<InfoBase> getRepository() {
        return this.infoBasesRepository;
    }
}
