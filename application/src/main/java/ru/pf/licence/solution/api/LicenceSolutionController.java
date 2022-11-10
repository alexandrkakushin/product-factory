package ru.pf.licence.solution.api;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.InfoBase;
import ru.pf.entity.licence.LicenceBuildScript;
import ru.pf.entity.licence.journal.JournalBuildSolution;
import ru.pf.licence.LicenceException;
import ru.pf.licence.solution.LicenceSolutionGenerator;
import ru.pf.repository.InfoBaseCrudRepository;
import ru.pf.repository.licence.JournalBuildSolutionCrudRepository;
import ru.pf.repository.licence.LicenceBuildScriptCrudRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/licence/solution")
public class LicenceSolutionController {

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
     * Репозиторий для добавления записей о ходе построения защищенного решения
     */
    @Autowired
    private JournalBuildSolutionCrudRepository journal;

    /**
     * Формирование защищенного решения в асинхронном режиме
     * Для этого в БД регистрируется запись с уникальным идентификатором, который передается клиенту для построения ссылки
     *
     * @param scriptId Идентификатор сценария сборки
     * @param infoBaseId Идентификатор информационной базы
     * @return Ответ сервера, содержащий уникальный идентификатор
     */
    @PostMapping("/async/build")
    @ApiOperation(value = "Формирование защищенного решения в асинхронном режиме")
    public ResponseAsyncBuild asyncBuildSolution(
            @RequestParam(name = "script") long scriptId,
            @RequestParam(name = "infobase") long infoBaseId) {

        ResponseAsyncBuild response = new ResponseAsyncBuild();

        Optional<LicenceBuildScript> buildScriptOptional = repository.findById(scriptId);
        Optional<InfoBase> infoBaseOptional = infoBaseCrudRepository.findById(infoBaseId);

        if (buildScriptOptional.isPresent() && infoBaseOptional.isPresent()) {
            UUID session = UUID.randomUUID();
            response.setUuid(session);

            LicenceBuildScript buildScript = buildScriptOptional.get();
            InfoBase infoBase = infoBaseOptional.get();

            Thread thBuild = new Thread(() -> {
                JournalBuildSolution newRecord = journal.newInstance();
                newRecord.setOperation(session);
                newRecord.setLicenceBuildScript(buildScript);
                newRecord.setCreated(new Date());
                try {
                    Path makeFile = solutionGenerator.build(session, buildScript, infoBase);
                    newRecord.setSuccessful(true);
                    newRecord.setFileNameForDownload(makeFile.toAbsolutePath().toString());
                    newRecord.setSize(makeFile.toFile().length());

                } catch (LicenceException | IOException e) {
                    e.printStackTrace();
                    newRecord.setTextError(e.getMessage());
                }
                journal.save(newRecord);
            });
            thBuild.setName("build-solution-" + scriptId);
            thBuild.start();

        } else {
            if (buildScriptOptional.isEmpty()) {
                response.setError("Не найден сценарий сборки по переданному идентификатору");
            } else {
                response.setError("Не найдена информационная база по переданному идентификатору");
            }
        }
        return response;
    }

    /**
     * Получение сформированного защищенного решения по идентификатору сессии
     * @param session Идентификатор сессии (UUID)
     * @return "Файл"
     * @throws FileNotFoundException Исключение может быть вызвано в случае, если файл не найден, но в БД есть о нем запись
     */
    @GetMapping("/async/download")
    @ApiOperation(value = "Скачивание защищенного решения")
    public ResponseEntity<InputStreamResource> asyncDownloadSolution(
            @RequestParam(name = "session") UUID session) throws FileNotFoundException {

        Optional<JournalBuildSolution> optionalDb = journal.findByOperation(session);
        if (optionalDb.isPresent()) {
            Path dataFile = Paths.get(optionalDb.get().getFileNameForDownload());

            InputStreamResource resource = new InputStreamResource(
                    new FileInputStream(dataFile.toFile()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + optionalDb.get().getFileNameForDownload())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(optionalDb.get().getSize())
                    .body(resource);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/async/status")
    public ResponseStatus status(
            @RequestParam(name = "session") UUID session) {
        ResponseStatus response = new ResponseStatus();
        Optional<JournalBuildSolution> optionalDb = journal.findByOperation(session);
        if (optionalDb.isPresent()) {
            response.setFound(true);
            response.setSuccessful(optionalDb.get().isSuccessful());
            response.setTextError(optionalDb.get().getTextError());
        }
        return response;
    }

    /**
     * Ответ метода асинхронного построения защищенного решения
     */
    @Getter
    @Setter
    static class ResponseAsyncBuild {
        /**
         * Идентификатор сессии
         */
        private UUID uuid;

        /**
         * Ошибки, например, отсутствие записей в базе данных
         */
        private String error;
    }

    /**
     * Ответ метода получения статуса
     */
    @Getter
    @Setter
    static class ResponseStatus {
        /**
         * Истина, если запись о решении найдена в БД
         */
        private boolean isFound;

        /**
         * Признак успешного построения решения
         */
        private boolean isSuccessful;

        /**
         * Ошибки
         */
        private String textError;
    }
}
