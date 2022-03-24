package ru.pf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.licence.BatchModeLicence;
import ru.pf.repository.licence.LicenceKeyCrudRepository;

/**
 * Контроллер для создания защащенных обработок
 * @author a.kakushin
 */
@Controller
@RequestMapping("/licence/generate")
public class LicenceGenerateController {

    @Autowired
    private BatchModeLicence batchModeLicence;

    @Autowired
    private LicenceKeyCrudRepository repository;

}
