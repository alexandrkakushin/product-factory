package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Property;
import ru.pf.repository.PfRepository;
import ru.pf.repository.PropertiesRepository;
import ru.pf.utility.Properties;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(PropertiesCrudController.URL)
public class PropertiesCrudController implements PfCrudController<Property> {

    static final String URL = "tools/properties";

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "property-item";
    }

    @Override
    public String getTemplateList() {
        return getUrl() + "/items";
    }

    @Override
    public String getName() {
        return "Настройки";
    }

    @Override
    public PfRepository<Property> getRepository() {
        return this.propertiesRepository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("propertyList", Properties.available());
    }
}
