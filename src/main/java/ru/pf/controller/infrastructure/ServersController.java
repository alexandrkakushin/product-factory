package ru.pf.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.PfController;
import ru.pf.entity.Server;
import ru.pf.repository.PfRepository;
import ru.pf.repository.ServersRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(ServersController.url)
public class ServersController implements PfController<Server, Long> {

    final static String url = "infrastructure/servers";

    @Autowired
    private ServersRepository serversRepository;

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
    public PfRepository<Server, Long> getRepository() {
        return this.serversRepository;
    }
}
