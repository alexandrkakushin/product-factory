package ru.pf.controller.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.PfController;
import ru.pf.entity.Property;
import ru.pf.repository.PfRepository;
import ru.pf.repository.PropertiesRepository;
import ru.pf.utility.Properties;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(PropertiesController.url)
public class PropertiesController implements PfController<Property, Long> {

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
