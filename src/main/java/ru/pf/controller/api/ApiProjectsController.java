package ru.pf.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pf.controller.development.ConfController;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;

import java.io.IOException;
import java.util.Optional;

/**
 * @author a.kakushin
 */
@RestController
@RequestMapping(path = "/api/projects")
public class ApiProjectsController {

    @Autowired
    ProjectsService projectsService;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ConfController confController;


    @GetMapping("/{id}/git/fetch")
    public ResponseEntity<?> gitFetch(@PathVariable(name = "id") Long id) throws IOException {
        return confController.gitFetch(id);
    }

    @GetMapping("/{id}/conf")
    public ResponseEntity<Conf> conf(@PathVariable(name = "id") Long id) throws IOException {
        Conf body = null;

        Optional<Project> project = projectsRepository.findById(id);
        if (project.isPresent()) {
            body = projectsService.getConfFromGit(project.get());
        }

        return new ResponseEntity<>(body, body != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
