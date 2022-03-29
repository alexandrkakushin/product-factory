package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Cr;
import ru.pf.repository.CrCrudRepository;
import ru.pf.repository.DesignerCrudRepository;
import ru.pf.repository.PfCrudRepository;

/**
 * Контроллер для реализации CRUD-операций с сущностью "Хранилище конфигурации"
 * @author a.kakushin
 */
@Controller
@RequestMapping(CrCrudController.URL)
public class CrCrudController implements PfCrudController<Cr> {

    static final String URL = "vcs/cr";

    @Autowired
    private CrCrudRepository crRepository;

    @Autowired
    private DesignerCrudRepository designerRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "cr-item";
    }

    @Override
    public String getName() {
        return "Хранилища конфигураций";
    }

    @Override
    public PfCrudRepository<Cr> getRepository() {
        return this.crRepository;
    }
}
