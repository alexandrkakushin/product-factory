package ru.pf.controller.vcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.controller.PfController;
import ru.pf.entity.Cr;
import ru.pf.repository.CrRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(CrController.url)
public class CrController implements PfController<Cr, Long> {

    final static String url = "vcs/cr";

    @Autowired
    private CrRepository crRepository;

    @Override
    public String getUrl() {
        return url;
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
    public PfRepository<Cr, Long> getRepository() {
        return this.crRepository;
    }

}
