package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Server;
import ru.pf.repository.OsRepository;
import ru.pf.repository.PfRepository;
import ru.pf.repository.ServersRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(ServersCrudController.url)
public class ServersCrudController implements PfCrudController<Server> {

    final static String url = "infrastructure/servers";

    @Autowired
    private ServersRepository serversRepository;

    @Autowired
    private OsRepository osRepository;

    @Override
    public String getUrl() {
        return url;
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
    public PfRepository<Server> getRepository() {
        return this.serversRepository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("osList", osRepository.findAll(Sort.by("id")));
    }


    @PostMapping("/submit")
    public String submit(@ModelAttribute Server entity) {
        if (entity.getOs() != null) {
            if (entity.getOs().getId() == null) {
                entity.setOs(null);
            }
        }
        Server saved = this.getRepository().save(entity);
        return "redirect:/" + url + "/" + saved.getId();
    }

}
