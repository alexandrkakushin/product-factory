package ru.pf.controller.simple.licence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.InfoBase;
import ru.pf.entity.licence.LicenceBuildScript;
import ru.pf.repository.licence.LicenceBuildScriptCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Контроллер для создания защищенных решений
 */
@Controller
@RequestMapping(LicenceSolutionGeneratorController.URL)
public class LicenceSolutionGeneratorController {

    static final String URL = "licence/solution/generate";

    /**
     * Репозиторий для чтения сценариев сборки
     */
    @Autowired
    private LicenceBuildScriptCrudRepository buildScriptRepository;

    @GetMapping
    String page(Model model) {
        List<BuildScript> buildScriptList = StreamSupport
                .stream(buildScriptRepository.findAll(Sort.by("name")).spliterator(), false)
                .map(BuildScript::new)
                         .collect(Collectors.toList());
        model.addAttribute("buildScriptList", buildScriptList);

        return URL + "/generate";
    }

    /**
     * Отдельный класс для передачи на клиент минимальной информации
     */
    @Getter
    @Setter
    static class BuildScript {
        /**
         * Идентификатор сценария
         */
        private Long id;

        /**
         * Имя сценария
         */
        private String name;

        /** Идентификатор информационной базы */
        private Long infoBaseId;

        /**
         * Конструктор на основании полного объекта
         * @param licenceBuildScript Сценарий сборки
         */
        public BuildScript(LicenceBuildScript licenceBuildScript) {
            this.id = licenceBuildScript.getId();
            this.name = licenceBuildScript.getName();
            this.infoBaseId = Optional.ofNullable(licenceBuildScript.getInfoBase()).map(InfoBase::getId).orElse(null);
        }
    }
}
