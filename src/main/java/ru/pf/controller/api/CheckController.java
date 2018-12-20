package ru.pf.controller.api;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;
import ru.pf.service.conf.check.NameLengthCheck;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * @author a.kakushin
 */
@RestController
@RequestMapping(path = "/api/conf/check")
public class CheckController {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    NameLengthCheck nameLengthCheck;

    @GetMapping("/namelength")
    public ResponseCheck checkNameLength(@RequestParam(name = "project") Long projectId) throws IOException, InvocationTargetException, IllegalAccessException {
        Optional<Project> project = projectsRepository.findById(projectId);
        if (project.isPresent()) {
            Conf conf = projectsService.getConfFromGit(project.get());
            return new ResponseCheck(nameLengthCheck.check(conf));
        }
        return null;
    }

    @Data
    private static class ResponseCheck {
        private boolean result;
        private List<MetadataObject> objects;

        public ResponseCheck(List<MetadataObject> objects) {
            if (objects != null) {
                this.result = (objects.size() > 0);
            }
            this.objects = objects;
        }
    }
}
