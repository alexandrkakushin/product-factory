package ru.pf.controller.development.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.Project;
import ru.pf.metadata.annotation.MetadataAnnotations;
import ru.pf.metadata.object.IMetadataObject;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.repository.ProjectsCrudRepository;
import ru.pf.service.ProjectsService;

import java.util.*;
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
    ProjectsCrudRepository projectsRepository;

    @Autowired
    MetadataAnnotations metadataAnnotations;

    @GetMapping("/{id}")
    public String conf(@PathVariable(name = "id") Long id, Model model) throws ReaderException {
        Optional<Project> projectOptional = projectsRepository.findById(id);
        if (projectOptional.isPresent()) {
            model.addAttribute("projectId", id);

            List<ConfObject> objects = projectsService.getConf(projectOptional.get()).getAllObjects()
                    .stream()
                    .map(ConfObject::new)
                    .collect(Collectors.toList());

            Set<Metadata> typesMetadata = objects
                    .stream()
                    .map(item -> new Metadata(item.getMetadataName(), item.getListPresentation()))
                    .collect(Collectors.toSet());

            model.addAttribute("objects", objects);
            model.addAttribute("typesMetadata", typesMetadata);
        }

        return "/development/conf/item";
    }

    @GetMapping("/{id}/object/{uuid}")
    public String metadata(@PathVariable(name = "id") Long id, @PathVariable(name = "uuid") String uuid, Model model) throws ReaderException {
        Optional<Project> projectOptional = projectsRepository.findById(id);
        if (projectOptional.isPresent()) {
            model.addAttribute("projectId", id);
        }

        MetadataObject object;

        if (projectOptional.isPresent()) {
            object = (MetadataObject) projectsService.getConf(projectOptional.get()).getObject(UUID.fromString(uuid));

            if (object != null) {
                object.parse();
                model.addAttribute("object", object);

                Map<String, String> fields = new HashMap<>();

                Arrays.stream(object.getClass().getDeclaredFields()).forEach(
                        field -> {
                            field.setAccessible(true);
                            metadataAnnotations.getAvailable().forEach(
                                    annotation -> {
                                        if (field.isAnnotationPresent(annotation)) {
                                            fields.put(metadataAnnotations.toCamelCase(annotation.getSimpleName()), field.getName());
                                        }
                                    }
                            );
                        }
                );

                model.addAttribute("fields", fields);
                return "/development/conf/metadata-item/" + object.getClass().getSimpleName().toLowerCase();
            }
        }

        return "/error/404.html";
    }

    public static class Metadata {
        private String name;
        private String presentation;

        public Metadata() {}

        public Metadata(String name, String presentation) {
            this();
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

        public ConfObject(IMetadataObject metadataObject) {
            this();            
            this.uuid = ((MetadataObject) metadataObject).getUuid();
            this.name = ((MetadataObject) metadataObject).getName();
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