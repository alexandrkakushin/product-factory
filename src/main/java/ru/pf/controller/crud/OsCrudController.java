package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Os;
import ru.pf.repository.OsRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(OsCrudController.url)
public class OsCrudController implements PfCrudController<Os> {

    final static String url = "infrastructure/os";

    @Autowired
    private OsRepository osRepository;

    @Override
    public String getUrl() {
        return url;
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
    public PfRepository<Os> getRepository() {
        return this.osRepository;
    }
}
