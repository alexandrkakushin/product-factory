package ru.pf.controller.development.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.api.CheckController;
import ru.pf.entity.Project;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.conf.check.ServiceCheck;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping("/development/conf/check")
public class ConfCheckController {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    CheckController checkController;

    @GetMapping("/{id}")
    public String page(@PathVariable(name = "id") Long projectId, Model model) throws IOException {
    Optional<Project> projectOptional = projectsRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            model.addAttribute("projectId", projectId);
            model.addAttribute("services", checkController.getAvailableServices());
        }

        return "/development/conf/check";
    }
}
