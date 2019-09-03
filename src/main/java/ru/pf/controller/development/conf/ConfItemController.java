package ru.pf.controller.development.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Project;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping("/development/conf/item")
public class ConfItemController {

    @Autowired
    ProjectsService projectsService;

    @Autowired
    ProjectsRepository projectsRepository;

    @GetMapping("/{id}")
    public String conf(@PathVariable(name = "id") Long id, Model model) throws IOException {
        Optional<Project> projectOptional = projectsRepository.findById(id);
        if (projectOptional.isPresent()) {
            model.addAttribute("projectId", id);

            List<ConfObject> objects = projectsService.getConf(projectOptional.get()).getAllObjects()
                    .stream()
                    .map(item -> new ConfObject(item))
                    .collect(Collectors.toList());

            // todo: реализовать данный функционал в самой конфигурации
            Set<String> metadataNames = objects
                    .stream()
                    .map(item -> item.getMetadataName())
                    .collect(Collectors.toSet());

            model.addAttribute("objects", objects);
            model.addAttribute("metadataNames", metadataNames);
        }

        return "/development/conf/item";
    }

    @GetMapping("/{id}/{uuid}")
    public String metadata(@PathVariable(name = "id") Long id, @PathVariable(name = "uuid") String uuid, Model model) throws IOException {
        Optional<Project> projectOptional = projectsRepository.findById(id);
        if (projectOptional.isPresent()) {
            model.addAttribute("projectId", id);
        }

        MetadataObject<?> object = projectsService.getConf(projectOptional.get())
                .getObject(UUID.fromString(uuid));
        if (object != null) {
            model.addAttribute("object", ((AbstractObject<?>) object));
        }

        return "/development/conf/metadata-item";
    }

    public class ConfObject {
        
        private UUID uuid;
        private String name;
        private String metadataName;

        public ConfObject() {}

        public ConfObject(MetadataObject<?> metadataObject) {
            this();            
            this.uuid = ((AbstractObject<?>) metadataObject).getUuid();
            this.name = ((AbstractObject<?>) metadataObject).getName();
            this.metadataName = metadataObject.getMetadataName();
        }

        public UUID getUuid() {
            return this.uuid;
        }
        
        public String getName() {
            return this.name;
        }

        public String getMetadataName() {
            return metadataName;
        }
    }
}