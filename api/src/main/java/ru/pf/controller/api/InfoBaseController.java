package ru.pf.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pf.entity.Designer;
import ru.pf.entity.InfoBase;
import ru.pf.repository.DesignerCrudRepository;
import ru.pf.repository.InfoBaseCrudRepository;
import ru.pf.yellow.BatchModeYellow;
import ru.pf.yellow.Yellow;
import ru.pf.yellow.YellowException;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с информационными базами
 */
@RestController
@RequestMapping(path = "/api/infobases")
public class InfoBaseController {

    /**
     * Пакетный режим 1С:Предприятие
     */
    @Autowired
    private BatchModeYellow batchModeYellow;

    /**
     * Репозиторий для работы с информационными базами
     */
    @Autowired
    private InfoBaseCrudRepository ibRepository;

    /**
     * Репозиторий для работы с конфигураторами
     */
    @Autowired
    private DesignerCrudRepository designerRepository;

    /**
     * GET-метод для получения имен расширений информационной базы
     * Если параметр "designer" не указан, будет осуществлен поиск первого конфигуратора из соответствующей таблицы
     *
     * @param idInfoBase Идентификатор базы данных
     * @param idDesigner Идентификатор конфигуратора, необязательный параметр
     * @return GetExtensionsResponse
     */
    @GetMapping("/extensions")
    @ApiOperation(value = "Получение расширений информационной базы")
    public ResponseEntity<GetExtensionsResponse> getExtensions(
            @RequestParam(name = "infobase") Long idInfoBase,
            @RequestParam(name = "designer", required = false) Long idDesigner) {

        GetExtensionsResponse response = new GetExtensionsResponse();

        try {
            Designer designer = null;
            if (idDesigner != null) {
                Optional<Designer> designerOptional = designerRepository.findById(idDesigner);
                if (designerOptional.isPresent()) {
                    designer = designerOptional.get();
                }
            } else {
                Iterator<Designer> designersIterator = designerRepository.findAll().iterator();
                if (designersIterator.hasNext()) {
                    designer = designersIterator.next();
                }
            }

            if (designer == null) {
                throw new ControllerException("Designer not found");
            }

            Optional<InfoBase> infoBaseOptional = ibRepository.findById(idInfoBase);
            if (infoBaseOptional.isPresent()) {
                Yellow yellow = new Yellow(Paths.get(designer.getPath()));

                ru.pf.yellow.InfoBase infoBase = new ru.pf.yellow.InfoBase(
                        Paths.get(infoBaseOptional.get().getPath()),
                        infoBaseOptional.get().getLogin(),
                        infoBaseOptional.get().getPassword());

                batchModeYellow.dumpDBCfgList(yellow, infoBase)
                        .forEach(item -> response.getItems().add(item.getName()));
            }

        } catch (ControllerException | YellowException ex) {
            response.setError(true);
            response.setDescription(ex.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Класс возврат метода GetExtensions
     */
    @Getter
    @Setter
    static class GetExtensionsResponse {

        /**
         * Имена расширений
         */
        private List<String> items;

        /**
         * Описание ошибки
         */
        private String description;

        /**
         * Признак ошибки
         */
        private boolean isError;

        /**
         * Конструктор по умолчанию
         */
        public GetExtensionsResponse() {
            this.items = new ArrayList<>();
        }
    }

    /**
     * Класс-исключение для методов контроллера
     */
    static class ControllerException extends Exception {

        /**
         * Конструктор с указанием текста исключения
         * @param message Текст исключения
         */
        public ControllerException(String message) {
            super(message);
        }
    }
}
