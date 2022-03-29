package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Os;
import ru.pf.repository.OsCrudRepository;
import ru.pf.repository.PfCrudRepository;

/**
 * Контроллер для реализации CRUD-операций с сущностью "Операционная система"
 * @author a.kakushin
 */
@Controller
@RequestMapping(OsCrudController.URL)
public class OsCrudController implements PfCrudController<Os> {

    static final String URL = "infrastructure/os";

    @Autowired
    private OsCrudRepository osRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "os-item";
    }

    @Override
    public String getName() {
        return "Операционные системы";
    }

    @Override
    public PfCrudRepository<Os> getRepository() {
        return this.osRepository;
    }
}
