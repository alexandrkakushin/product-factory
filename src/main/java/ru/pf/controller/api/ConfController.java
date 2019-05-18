package ru.pf.controller.api;

import lombok.Data;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.GitService;
import ru.pf.service.ProjectsService;

import java.io.IOException;
import java.util.Optional;

/**
 * @author a.kakushin
 */
@RestController
@RequestMapping(path = "/api/projects")
public class ConfController {

    @Autowired
    GitService gitService;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    ProjectsRepository projectsRepository;

    @PostMapping("/{id}/git/fetch")
    public ResponseEntity<?> gitFetch(@PathVariable(name = "id") Long id) throws IOException {
        ConfController.ResponseGit body = new ConfController.ResponseGit();
        body.setSuccess(false);

        projectsRepository.findById(id).ifPresent(
                (Project project) -> {
                    try {
                        gitService.fetch(project);
                        body.setSuccess(true);
                    } catch (IOException | GitAPIException ex) {
                        body.setDescription(ex.getLocalizedMessage());
                    }
                }
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{id}/conf")
    public ResponseEntity<Conf> conf(@PathVariable(name = "id") Long id) throws IOException {
        Conf body = null;

        Optional<Project> project = projectsRepository.findById(id);
        if (project.isPresent()) {
            body = projectsService.getConf(project.get());
        }

        return new ResponseEntity<>(body, body != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Data
    static class ResponseGit {
        private boolean success;
        private String description;

        public ResponseGit() {}

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
