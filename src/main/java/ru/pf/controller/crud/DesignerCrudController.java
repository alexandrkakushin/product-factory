package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.pf.entity.Designer;
import ru.pf.repository.DesignerRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(DesignerCrudController.URL)
public class DesignerCrudController implements PfCrudController<Designer> {

    static final String URL = "tools/designers";

    @Autowired
    private DesignerRepository repository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "designer-item";
    }

    @Override
    public String getName() {
        return "Конфигураторы";
    }

    @Override
    public PfRepository<Designer> getRepository() {
        return this.repository;
    }
}
