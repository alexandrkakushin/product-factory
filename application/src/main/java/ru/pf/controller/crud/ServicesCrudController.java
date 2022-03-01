package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Service;
import ru.pf.repository.PfRepository;
import ru.pf.repository.ServicesRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(ServicesCrudController.URL)
public class ServicesCrudController implements PfCrudController<Service> {

    static final String URL = "infrastructure/services";

    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    public String getUrl() {
        return URL;
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
    public PfRepository<Service> getRepository() {
        return this.servicesRepository;
    }
}
