package ru.pf.controller.api;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.reader.ConfReader;
import ru.pf.service.PropertiesService;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@RestController
@RequestMapping(path = "/api/projects")
public class ApiProjectsController {

    @Autowired
    ConfReader confReader;

    @Autowired
    PropertiesService propertiesService;

    @GetMapping("/{id}/git/fetch")
    public ResponseEntity<ResponseGitFetch> gitFetch(@PathVariable(name = "id") Long id) {
        ResponseGitFetch body = new ResponseGitFetch();

        Path storage = propertiesService.getStorage();

        body.setSuccess(true);
        body.setDescription("tro-lo-lo");

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{id}/conf")
    public ResponseEntity<Conf> conf(@PathVariable(name = "id") Long id) throws IOException {

        Path storage = propertiesService.getStorage();
        Path target = storage
                .resolve(id.toString())
                .resolve("git");

        return new ResponseEntity<>(confReader.read(target), HttpStatus.OK);
    }

    @Data
    static class ResponseGitFetch {
        private boolean success;
        private String description;

        public ResponseGitFetch() {}

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
