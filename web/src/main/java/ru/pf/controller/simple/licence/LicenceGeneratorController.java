package ru.pf.controller.simple.licence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.repository.licence.LicenceBuildScriptCrudRepository;

/**
 * Контроллер для создания защищенных обработок
 */
@Controller
@RequestMapping(LicenceGeneratorController.URL)
public class LicenceGeneratorController {

    static final String URL = "licence/generate";

    /**
     * Репозиторий для чтения сценариев сборки
     */
    @Autowired
    private LicenceBuildScriptCrudRepository buildScriptRepository;

    @GetMapping
    String page(Model model) {
        model.addAttribute("buildScriptList", buildScriptRepository.findAll(Sort.by("name")));

        return URL + "/generate";
    }
}
