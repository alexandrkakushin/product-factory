package ru.pf.licence.solution.api;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pf.entity.InfoBase;
import ru.pf.entity.licence.LicenceBuildScript;
import ru.pf.licence.LicenceException;
import ru.pf.licence.solution.LicenceSolutionGenerator;
import ru.pf.repository.InfoBaseCrudRepository;
import ru.pf.repository.licence.LicenceBuildScriptCrudRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/licence/solution")
public class LicenceSolutionGeneratorController {

    /**
     * Сервис построения защищенных решений
     */
    @Autowired
    private LicenceSolutionGenerator solutionGenerator;

    /**
     * Репозиторий для работы со сценариями сборки
     */
    @Autowired
    private LicenceBuildScriptCrudRepository repository;

    /**
     * Репозиторий для поиска информационной базы для построения защищенного решения
     */
    @Autowired
    private InfoBaseCrudRepository infoBaseCrudRepository;

    /**
     * Формирование защищенного решения
     * @param scriptId Идентификатор сценария сборки
     * @param infoBaseId Идентификатор информационной базы для сборки
     * @return "cf/cfe-файл"
     */
    @GetMapping("/sync/build")
    @ApiOperation(value = "Формирование защищенного решения")
    public ResponseEntity<InputStreamResource> buildSolution(
            @RequestParam(name = "script") long scriptId,
            @RequestParam(name = "infobase") long infoBaseId) throws LicenceException, IOException {

        Optional<LicenceBuildScript> buildScriptOptional = repository.findById(scriptId);
        Optional<InfoBase> infoBaseOptional = infoBaseCrudRepository.findById(infoBaseId);

        if (buildScriptOptional.isPresent() && infoBaseOptional.isPresent()) {
            Path makeFile = solutionGenerator.build(buildScriptOptional.get(), infoBaseOptional.get());
            try (FileInputStream fio = new FileInputStream(makeFile.toFile())) {
                InputStreamResource resource = new InputStreamResource(fio);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + makeFile.getFileName())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(makeFile.toFile().length())
                        .body(resource);
            } finally {
                Files.deleteIfExists(makeFile);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/async/build")
    public ResponseAsyncBuildSolution asyncBuildSolution(
            @RequestParam(name = "script") long scriptId) throws LicenceException, IOException {
        ResponseAsyncBuildSolution response = new ResponseAsyncBuildSolution();



        return response;
    }

    @GetMapping("/async/download")
    public ResponseEntity<InputStreamResource> asyncDownloadSolution(
            @RequestParam(name = "uuid") UUID uuid) throws IOException {
        return null;
    }

    @Getter
    @Setter
    static class ResponseAsyncBuildSolution {
        private UUID uuid;

        public ResponseAsyncBuildSolution() {
            this.uuid = UUID.randomUUID();
        }
    }
}
