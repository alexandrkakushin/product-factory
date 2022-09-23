package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Server;
import ru.pf.repository.OsCrudRepository;
import ru.pf.repository.PfCrudRepository;
import ru.pf.repository.ServersCrudRepository;

/**
 * Контроллер для реализации CRUD-операций с сущностью "Серверы"
 * @author a.kakushin
 */
@Controller
@RequestMapping(ServersCrudController.URL)
public class ServersCrudController implements PfCrudController<Server> {

    static final String URL = "infrastructure/servers";

    @Autowired
    private ServersCrudRepository serversRepository;

    @Autowired
    private OsCrudRepository osRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "server-item";
    }

    @Override
    public String getName() {
        return "Серверы";
    }

    @Override
    public PfCrudRepository<Server> getRepository() {
        return this.serversRepository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("osList", osRepository.findAll(Sort.by("id")));
    }

    @Override
    public String submit(@ModelAttribute Server entity) throws SubmitException {
        if (entity.getOs() != null) {
            if (entity.getOs().getId() == null) {
                entity.setOs(null);
            }
        }
        return PfCrudController.super.submit(entity);
    }
}