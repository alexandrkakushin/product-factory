package ru.pf.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pf.yellow.BatchModeYellow;

import java.util.Arrays;
import java.util.List;

/**
 * Контроллер для работы с информационными базами
 */
@RestController
@RequestMapping(path = "/api/infobases")
public class InfoBaseController {

    @Autowired
    private BatchModeYellow batchModeYellow;

    @GetMapping("/extensions")
    @ApiOperation(value = "Получение расширений информационной базы")
    public List<String> getExtensions(Long idInfoBase) {
        return Arrays.asList("Test", "Test2");
    }
}
