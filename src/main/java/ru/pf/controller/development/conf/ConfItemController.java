package ru.pf.controller.development.conf;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import ru.pf.metadata.annotation.CommandModule;
import ru.pf.metadata.annotation.Forms;
import ru.pf.metadata.annotation.ManagerModule;
import ru.pf.metadata.annotation.ObjectModule;
import ru.pf.metadata.annotation.PlainModule;
import ru.pf.metadata.annotation.RecordSetModule;
import ru.pf.metadata.annotation.ValueManagerModule;
import ru.pf.metadata.object.AbstractMetadataObject;
import ru.pf.metadata.object.Catalog;
import ru.pf.metadata.object.Enum;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.object.common.CommonForm;
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

    @GetMapping("/{id}/object/{uuid}")
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

        Map<String, String> fields = new HashMap<>();
        // annotation
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            // @Forms
            if (field.isAnnotationPresent(Forms.class)) {
                fields.put("forms", field.getName());

            // @ObjectModule
            } else if (field.isAnnotationPresent(ObjectModule.class)) {
                fields.put("objectModule", field.getName());

            // @ManagerModule
            } else if (field.isAnnotationPresent(ManagerModule.class)) {
                fields.put("managerModule", field.getName());

            // @RecordSetModule
            } else if (field.isAnnotationPresent(RecordSetModule.class)) {
                fields.put("recordSetModule", field.getName());

            // @PlainModule
            } else if (field.isAnnotationPresent(PlainModule.class)) {
                fields.put("plainModule", field.getName());
            
            // @ValueManagerModule
            } else if (field.isAnnotationPresent(ValueManagerModule.class)) {
                fields.put("valueManagerModule", field.getName());

            // @CommandModule
            } else if (field.isAnnotationPresent(CommandModule.class)) {
                fields.put("commandModule", field.getName());
            }
        }        
        model.addAttribute("fields", fields);

        // TODO: получать по имени класса
        String pathModel = "/development/conf/metadata-item/common";
        if (object instanceof Enum) {
            pathModel = "/development/conf/metadata-item/enum";
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