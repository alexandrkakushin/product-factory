package ru.pf.controller.development.conf;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.pf.entity.Project;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.object.Catalog;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.object.common.CommonModule;
import ru.pf.metadata.object.common.Language;
import ru.pf.repository.ProjectsRepository;
import ru.pf.service.ProjectsService;

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
            Set<Metadata> typesMetadata = objects
                    .stream()
                    .map(item -> new Metadata(item.getMetadataName(), item.getListPresentation()))
                    .collect(Collectors.toSet());

            model.addAttribute("objects", objects);
            model.addAttribute("typesMetadata", typesMetadata);
        }

        return "/development/conf/item";
    }

    @GetMapping("/{id}/{uuid}")
    public String metadata(@PathVariable(name = "id") Long id, @PathVariable(name = "uuid") String uuid, Model model) throws IOException {
        Optional<Project> projectOptional = projectsRepository.findById(id);
        if (projectOptional.isPresent()) {
            model.addAttribute("projectId", id);
        }

        MetadataObject object = projectsService.getConf(projectOptional.get())
                .getObject(UUID.fromString(uuid));
        if (object != null) {
            model.addAttribute("object", ((AbstractMetadataObject) object));
        }

        // TODO: получать по имени класса
        String pathModel = "/development/conf/metadata-item/common";
        if (object instanceof CommonModule) {
            pathModel = "/development/conf/metadata-item/commonmodule";

        } else if (object instanceof Language) {
            pathModel = "/development/conf/metadata-item/language";

        } else if (object instanceof Catalog) {
            pathModel = "/development/conf/metadata-item/catalog";
        }

        return pathModel;
    }

    public static class Metadata {
        private String name;
        private String presentation;

        public Metadata() {}

        public Metadata(String name, String presentation) {
            this.name = name;
            this.presentation = presentation;
        }

        public String getName() {
            return name;
        }

        public String getPresentation() {
            return presentation;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj instanceof Metadata) {
                return obj.hashCode() == this.hashCode();
            } else {
                return false;
            }
        }        
    } 

    public static class ConfObject {        
        private UUID uuid;
        private String name;
        private String metadataName;
        private String listPresentation;

        public ConfObject() {}

        public ConfObject(MetadataObject metadataObject) {
            this();            
            this.uuid = ((AbstractMetadataObject) metadataObject).getUuid();
            this.name = ((AbstractMetadataObject) metadataObject).getName();
            this.metadataName = metadataObject.getXmlName();
            this.listPresentation = metadataObject.getListPresentation();
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

        public String getListPresentation() {
            return this.listPresentation;
        }
    }
}