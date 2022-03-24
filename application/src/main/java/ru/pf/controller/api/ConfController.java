package ru.pf.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.repository.ProjectsCrudRepository;
import ru.pf.service.ProjectsService;
import ru.pf.service.ZipService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * @author a.kakushin
 */
@RestController
@RequestMapping(path = "/api/projects")
public class ConfController {

    @Autowired
    ZipService zipService;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    ProjectsCrudRepository projectsRepository;

    @PostMapping("/{id}/update")
    public ResponseEntity<ResponseUpdate> update(@PathVariable(name = "id") Long id) {
        ResponseUpdate body = new ConfController.ResponseUpdate();
        body.setSuccess(false);

        projectsRepository.findById(id).ifPresent((Project project) -> {
            try {
                projectsService.update(project);
                body.setSuccess(true);
            } catch (Exception ex) {
                body.setDescription(ex.getLocalizedMessage());
            }
        });

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{id}/conf")
    public ResponseEntity<Conf> conf(@PathVariable(name = "id") Long id) throws ReaderException {
        Conf body = null;

        Optional<Project> project = projectsRepository.findById(id);
        if (project.isPresent()) {
            body = projectsService.getConf(project.get());
        }

        return new ResponseEntity<>(body, body != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/conf/download")
    public ResponseEntity<InputStreamResource> downloadZip(@PathVariable(name = "id") Long id)
            throws IOException {

        Optional<Project> projectOptional = projectsRepository.findById(id);
        if (projectOptional.isPresent()) {
            Path location = projectsService.getTemporaryLocation(projectOptional.get());
            if (Files.exists(location)) {
                ByteArrayOutputStream baos = zipService.createWithSubDir(location);
                InputStreamResource resource = new InputStreamResource(
                        new ByteArrayInputStream(baos.toByteArray()));

                String fileName = "project_" + projectOptional.get().getId() + "_conf.zip";

                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(baos.size())
                        .body(resource);
            }
        }

        return null;
    }

    static class ResponseUpdate {
        private boolean success;
        private String description;

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getDescription() {
            return description;
        }
    }
}