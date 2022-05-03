package ru.pf.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.reader.ConfReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.repository.ProjectsCrudRepository;
import ru.pf.service.PropertiesService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с метаданными
 */
@RestController
@RequestMapping(path = "/api/conf")
public class ConfController {

    /**
     * Репозиторий для чтения проектов
     */
    @Autowired
    private ProjectsCrudRepository projectsCrudRepository;

    /**
     * Сервис для чтения параметров приложения
     */
    @Autowired
    private PropertiesService propertiesService;

    /**
     * Сервис для чтения метаданных конфигурации или расширения
     */
    @Autowired
    private ConfReader confReader;

    /**
     * Получение списка метаданных с отбором по типу метаданных
     * @param idProject Идентификатор проекта
     * @param objectMetadata Тип метаданных, должно совпадать с именем класса
     * @return Список объектов в виде "UUID, Наименование"
     */
    @GetMapping("/objects")
    @ApiOperation(value = "Получение списка метаданных в виде <UUID, Наименование>")
    public ResponseEntity<GetObjectsResponse> getObjects(
            @RequestParam(name = "project") long idProject,
            @RequestParam(name = "objMetadata", required = false) String objectMetadata) {

        GetObjectsResponse response = new GetObjectsResponse();

        Optional<Project> optionalProject = projectsCrudRepository.findById(idProject);
        if (optionalProject.isEmpty()) {
            response.setError("Проект не найден в базе данных");
            return ResponseEntity.ok(response);
        }

        Path root = propertiesService.getStorageProject(optionalProject.get());
        if (!Files.exists(root.resolve("Configuration.xml"))) {
            response.setError("Не найдены метаданные конфигурации. Обновите проект и повторите попытку");
            return ResponseEntity.ok(response);
        }

        try {
            Conf conf = confReader.read(root);
            response.items = confReader.getRelations(conf).stream()
                    .filter(relation -> relation.getObjClass().getSimpleName().equalsIgnoreCase(objectMetadata))
                    .map(ConfReader.Relation::getContainer)
                    .flatMap(Collection::stream)
                    .map(MetadataObject.class::cast)
                    .map(metadataObject ->
                            new GetObjectsResponse.Item(
                                    metadataObject.getUuid(), metadataObject.getName()))
                    .collect(Collectors.toList());

        } catch (ReaderException ex) {
            response.setError(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Получение объекта метаданных по UUID
     * @param idProject Идентификатор проекта
     * @param uuidObject Идентификатор объекта метаданных
     * @return GetObjectResponse, в случае ошибки будут заполнены соответствующие поля
     */
    @GetMapping("/object")
    @ApiOperation(value = "Получение объекта метаданных")
    public ResponseEntity<GetObjectResponse> getObject(
            @RequestParam(name = "project") long idProject,
            @RequestParam(name = "uuid") UUID uuidObject) {

        GetObjectResponse response = new GetObjectResponse();

        Optional<Project> optionalProject = projectsCrudRepository.findById(idProject);
        if (optionalProject.isEmpty()) {
            response.setError("Проект не найден в базе данных");
            return ResponseEntity.ok(response);
        }

        Path root = propertiesService.getStorageProject(optionalProject.get());
        if (!Files.exists(root.resolve("Configuration.xml"))) {
            response.setError("Не найдены метаданные конфигурации. Обновите проект и повторите попытку");
            return ResponseEntity.ok(response);
        }

        try {
            Conf conf = confReader.read(root);

            response.setObject(
                confReader.getRelations(conf).stream()
                    .map(ConfReader.Relation::getContainer)
                    .flatMap(Collection::stream)
                    .map(MetadataObject.class::cast)
                    .filter(metadataObject -> metadataObject.getUuid().equals(uuidObject))
                    .findFirst()
                    .orElseThrow(() -> new ReaderException("Объект метаданных не найден")));

            response.getObject().parse();

        } catch (ReaderException ex) {
            response.setError(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Класс возврат метода GetObject
     */
    @Getter
    @Setter
    static class GetObjectResponse extends Response {

        /**
         * Объект метаданных
         */
        private MetadataObject object;
    }

    /**
     * Класс возврат метода GetObjects
     */
    @Getter
    @Setter
    static class GetObjectsResponse extends Response {

        /**
         * Список объектов метаданных
         */
        private List<Item> items;

        /**
         * Конструктор по умолчанию
         */
        public GetObjectsResponse() {
            this.items = new ArrayList<>();
        }

        /**
         * Класс для краткого описания объекта метаданных
         */
        @Getter
        @Setter
        @AllArgsConstructor
        static class Item {

            /**
             * Идентификатор объекта метаданных
             */
            private UUID uuid;

            /**
             * Имя объекта метаданных
             */
            private String name;
        }
    }

    /**
     * Класс возврат методов
     */
    @Getter
    @Setter
    abstract static class Response {

        /**
         * Описание ошибки
         */
        private String description;

        /**
         * Признак ошибки
         */
        private boolean isError;

        /**
         * Метод для фиксирования ошибки
         * @param description Описание ошибки
         */
        public void setError(String description) {
            this.isError = true;
            this.description = description;
        }
    }
}
