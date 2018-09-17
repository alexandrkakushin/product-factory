package ru.pf.controller.development;

import lombok.Data;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.controller.api.ApiProjectsController;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;
import ru.pf.service.PropertiesService;

import java.io.IOException;
import java.util.Optional;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping("/development/projects")
public class ConfController {

    @Autowired
    PropertiesService propertiesService;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ProjectsService projectsService;

    @GetMapping("/{id}/conf")
    public String conf(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("id", id);
        return "/development/projects/project-conf";
    }



    @GetMapping("/{id}/conf/git/fetch")
    public ResponseEntity<?> gitFetch(@PathVariable(name = "id") Long id) throws IOException {
        ConfController.ResponseGit body = new ConfController.ResponseGit();
        body.setSuccess(false);

        projectsRepository.findById(id).ifPresent(
                (Project project) -> {
                    try {
                        projectsService.gitFetch(project);
                        body.setSuccess(true);
                    } catch (IOException | GitAPIException ex) {
                        body.setDescription(ex.getLocalizedMessage());
                    }
                }
        );

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{id}/conf/metadata")
    public ResponseEntity<?> getMetadata(@PathVariable(name = "id") Long id) throws IOException {
        Conf conf = null;

        Optional<Project> project = projectsRepository.findById(id);
        if (project.isPresent()) {
            conf = projectsService.getConfFromGit(project.get());
        }

        return new ResponseEntity<>(conf, conf != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
