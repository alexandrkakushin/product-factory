package ru.pf.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.PfCrudController;
import ru.pf.entity.Service;
import ru.pf.repository.PfRepository;
import ru.pf.repository.ServicesRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(ServicesCrudController.url)
public class ServicesCrudController implements PfCrudController<Service, Long> {

    final static String url = "infrastructure/services";

    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getTemplateItem() {
        return "service-item";
    }

    @Override
    public String getName() {
        return "Сервисы";
    }

    @Override
    public PfRepository<Service, Long> getRepository() {
        return this.servicesRepository;
    }
}
