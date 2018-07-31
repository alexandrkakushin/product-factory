package ru.pf.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.controller.PfController;
import ru.pf.entity.Os;
import ru.pf.repository.OsRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(OsController.url)
public class OsController implements PfController<Os, Long> {

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
    public PfRepository<Os, Long> getRepository() {
        return this.osRepository;
    }
}
