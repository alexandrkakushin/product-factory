package ru.pf.controller.development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Project;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping("/development/conf")
public class ProjectConfController {

    @Autowired
    ProjectsService projectsService;

    @Autowired
    ProjectsRepository projectsRepository;

    @GetMapping("/{id}")
    public String conf(@PathVariable(name = "id") Long id, Model model) throws IOException {
        Optional<Project> projectOptional = projectsRepository.findById(id);
        if (projectOptional.isPresent()) {
            model.addAttribute("id", id);

            model.addAttribute("objects", projectsService.getConf(projectOptional.get()).getAllObjects()
                    .stream()
                    .collect(Collectors.toMap(
                            item -> ((AbstractObject) item).getUuid(),
                            item -> ((AbstractObject) item).getName())));
        }

        return "/development/conf/project-conf";
    }
}
