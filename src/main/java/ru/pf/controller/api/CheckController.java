package ru.pf.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Project;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;
import ru.pf.service.conf.check.NameLengthCheck;
import ru.pf.service.conf.check.SubsystemCheck;

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

    @Autowired
    SubsystemCheck subsystemCheck;

    @GetMapping("/namelength")
    @JsonView({MetadataJsonView.List.class})
    public ResponseCheck checkNameLength(@RequestParam(name = "project") Long projectId) throws IOException, InvocationTargetException, IllegalAccessException {
        Conf conf = getConfFromGit(projectId);
        if (conf != null) {
            return new ResponseCheck(nameLengthCheck.check(conf));
        }
        return null;
    }

    @GetMapping("/subsystem")
    @JsonView({MetadataJsonView.List.class})
    public ResponseCheck checkSubsystem(@RequestParam(name = "project") Long projectId) throws IOException {
        Conf conf = getConfFromGit(projectId);
        if (conf != null) {
            return new ResponseCheck(subsystemCheck.check(conf));
        }
        return null;
    }

    private Conf getConfFromGit(Long projectId) {
        Optional<Project> project = projectsRepository.findById(projectId);
        if (project.isPresent()) {
            try {
                return projectsService.getConfFromGit(project.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Data
    @JsonView({MetadataJsonView.List.class})
    private static class ResponseCheck {
        private boolean result;
        private List<MetadataObject> objects;

        public ResponseCheck(List<MetadataObject> objects) {
            if (objects != null) {
                this.result = (objects.size() == 0);
            }
            this.objects = objects;
        }
    }
}
