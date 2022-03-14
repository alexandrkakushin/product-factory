package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Cr;
import ru.pf.repository.CrRepository;
import ru.pf.repository.DesignerRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(CrCrudController.URL)
public class CrCrudController implements PfCrudController<Cr> {

    static final String URL = "vcs/cr";

    @Autowired
    private CrRepository crRepository;

    @Autowired
    private DesignerRepository designerRepository;

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
    public PfRepository<Cr> getRepository() {
        return this.crRepository;
    }
}
