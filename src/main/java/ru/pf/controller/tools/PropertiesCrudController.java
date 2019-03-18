package ru.pf.controller.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.PfCrudController;
import ru.pf.entity.Property;
import ru.pf.repository.PfRepository;
import ru.pf.repository.PropertiesRepository;
import ru.pf.utility.Properties;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(PropertiesCrudController.url)
public class PropertiesCrudController implements PfCrudController<Property, Long> {

    final static String url = "tools/properties";

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Override
    public String getUrl() {
        return url;
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
    public PfRepository<Property, Long> getRepository() {
        return this.propertiesRepository;
    }

    @Override
    public void addAttributesItem(Model model) {
        model.addAttribute("propertyList", Properties.availables());
    }
}
