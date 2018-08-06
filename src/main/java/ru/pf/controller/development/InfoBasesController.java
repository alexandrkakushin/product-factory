package ru.pf.controller.development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.PfController;
import ru.pf.entity.InfoBase;
import ru.pf.entity.Project;
import ru.pf.repository.InfoBasesRepository;
import ru.pf.repository.PfRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(InfoBasesController.url)
public class InfoBasesController implements PfController<InfoBase, Long> {

    final static String url = "development/infobases";

    @Autowired
    private InfoBasesRepository infoBasesRepository;

    @Override
    public String getUrl() {
        return url;
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
    public PfRepository<InfoBase, Long> getRepository() {
        return this.infoBasesRepository;
    }
}
