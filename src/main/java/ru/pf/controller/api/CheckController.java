package ru.pf.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Project;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.object.Conf;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;
import ru.pf.service.conf.check.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    DuplicateViewCheck duplicateViewCheck;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    NameLengthCheck nameLengthCheck;

    @Autowired
    SubsystemCheck subsystemCheck;

    @Autowired
    CommonModuleNameCheck commonModuleNameCheck;

    @Autowired
    NoReturnCheck noReturnCheck;

    @Autowired
    RefRefCheck refRefCheck;

    @Autowired
    EmptyMethodCheck emptyMethodCheck;

    @Autowired
    UseModalityCheck useModalityCheck;

    @Autowired
    LineSizeCheck lineSizeCheck;

    @GetMapping("/{service}")
    @JsonView(MetadataJsonView.List.class)
    public ResponseService check(
            @PathVariable(name = "service") String service, @RequestParam(name = "project") Long projectId) throws Exception {

        ServiceCheck<?> serviceCheck = getAvailableServices().get(service);
        if (serviceCheck != null) {
            Conf conf = getConf(projectId);
            if (conf != null) {
                // TODO: обработка исключений
                try {
                    return new ResponseService(getAvailableServices().get(service).check(conf));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new Exception("Service not found");
        }

        return null;
    }

    public Map<String, ServiceCheck> getAvailableServices() {
        Map<String, ServiceCheck> services = new HashMap<>();
        services.put("namelength", nameLengthCheck);
        services.put("subsystem", subsystemCheck);
        services.put("duplicateview", duplicateViewCheck);
        services.put("commonmodulename", commonModuleNameCheck);
        services.put("refref", refRefCheck);
        services.put("emptymethod", emptyMethodCheck);
        services.put("usemodality", useModalityCheck);
        services.put("linesize", lineSizeCheck);
        services.put("noreturn", noReturnCheck);

        return services;
    }

    private Conf getConf(Long projectId) {
        Optional<Project> project = projectsRepository.findById(projectId);
        if (project.isPresent()) {
            try {
                return projectsService.getConf(project.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Data
    @JsonView({MetadataJsonView.List.class})
    private static class ResponseService {
        private boolean result;
        private List<Object> objects;

        public ResponseService(List<Object> objects) {
            if (objects != null) {
                this.result = objects.isEmpty();
            }
            this.objects = objects;
        }
    }
}
