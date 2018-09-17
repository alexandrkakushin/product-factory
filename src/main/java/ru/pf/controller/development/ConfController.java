package ru.pf.controller.development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.service.PropertiesService;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping("/development/projects")
public class ConfController {

    @Autowired
    PropertiesService propertiesService;

    @GetMapping("/{id}/conf")
    public String conf(@PathVariable(name = "id") Long id, Model model) {
        return "/development/projects/project-conf";
    }
}
