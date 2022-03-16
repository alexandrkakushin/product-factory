package ru.pf.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pf.entity.licence.LicenceKey;
import ru.pf.entity.licence.Journal;
import ru.pf.licence.BatchModeLicence;
import ru.pf.licence.LicenceException;
import ru.pf.repository.licence.LicenceKeyRepository;
import ru.pf.repository.licence.JournalRepository;
import ru.pf.service.PropertiesService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/licence")
public class LicenceController {

    /**
     * Репозиторий для доступа к сохраненным ключам СЛК
     */
    @Autowired
    private LicenceKeyRepository licenceKeyRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private BatchModeLicence batchMode;

    /**
     * Получение списка доступных ключей СЛК
     * @return Список наименований ключей
     */
    @GetMapping("/keys")
    @ApiOperation(value = "Получение списка доступных ключей СЛК")
    public List<KeyView> getKeys() {
        List<KeyView> keys = new ArrayList<>();
        licenceKeyRepository.findAll()
                .forEach(item -> keys.add(
                        new KeyView(item.getId(), item.getName())));

        return keys;
    }

    /**
     * Представление ключа СЛК
     */
    @Getter
    static class KeyView {

        /**
         * Идентификатор записи
         */
        private Long id;

        /**
         * Наименование ключа (серия)
         */
        private String name;

        /**
         * Конструктор с указанием идентификатора записи и наименования (серии) ключа
         * @param id Идентификатор записи
         * @param name Наименование (серия) ключа
         */
        public KeyView(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    /**
     * Формирование защищенной обработки
     * @param id Идентификатор ключа
     * @param multipartFile Обработка (*.epf файл)
     * @return Результат выполнения, содержащий идентификатор операции и дополнительную информацию
     */
    @PostMapping("/generate")
    @ApiOperation(value = "Формирование защищенной обработки")
    public ResponseGenerate generate(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "file") MultipartFile multipartFile) {

        ResponseGenerate response = new ResponseGenerate();

        try {
            Optional<LicenceKey> licenceKeyOptional = this.licenceKeyRepository.findById(id);

            if (licenceKeyOptional.isPresent()) {
                Path keyPath = propertiesService.getStorage()
                        .resolve("licence")
                        .resolve(licenceKeyOptional.get().getName());

                UUID uuidOperation = UUID.randomUUID();
                Path cryptKey = keyPath.resolve(licenceKeyOptional.get().getFileName());
                Path dataProcessing = keyPath.resolve(uuidOperation + ".epf");
                Path protectedModule = keyPath.resolve(uuidOperation + ".datafile");

                if (!Files.exists(keyPath)) {
                    Files.createDirectories(keyPath);
                }

                Files.write(cryptKey, licenceKeyOptional.get().getAttachedFile());
                Files.write(dataProcessing, multipartFile.getBytes());

                batchMode.createLicenceObjectsFile(
                        propertiesService.getLicenceAppFile(),
                        protectedModule,
                        dataProcessing,
                        cryptKey,
                        licenceKeyOptional.get().getName()
                );

                response.setUuid(uuidOperation);
                response.setSize(protectedModule.toFile().length());

                Journal journal = new Journal();
                journal.setOperation(uuidOperation);
                journal.setDataProcessing(dataProcessing.toFile().getAbsolutePath());
                journal.setProtectedModule(protectedModule.toFile().getAbsolutePath());
                journal.setFileNameForDownload(licenceKeyOptional.get().getName() + ".datafile");
                journal.setSize(protectedModule.toFile().length());

                journalRepository.save(journal);

            } else {
                throw new LicenceException("Key not found");
            }

        } catch (IOException | LicenceException ex) {
            response.setError(true);
            response.setDescription(ex.getLocalizedMessage());
        }

        return response;
    }

    /**
     * Ответ сервиса генерации защищенной обработки
     */
    @Getter
    @Setter
    static class ResponseGenerate {
        /**
         * Идентификатор операции
         */
        private UUID uuid;

        /**
         * Размер файла защищенной обработки (*.datafile)
         */
        private long size;

        /**
         * Истина, в случае ошибки
         */
        private boolean error;

        /**
         * Описание ошибки
         */
        private String description;
    }

    /**
     * Получение сгенерированного ранее защищенного модуля
     * @param uuidOperation
     * @return "Файл"
     * @throws FileNotFoundException Исключение может быть вызвано в случае, если файл не найден, но в БД есть о нем запись
     */
    @GetMapping("/download")
    @ApiOperation(value = "Получение сгенерированного ранее защищенного модуля")
    public ResponseEntity<InputStreamResource> download(@RequestParam("uuid") UUID uuidOperation) throws FileNotFoundException {

        Optional<Journal> optionalDb = journalRepository.findByOperation(uuidOperation);
        if (optionalDb.isPresent()) {
            Path dataFile = Paths.get(optionalDb.get().getProtectedModule());

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
}
