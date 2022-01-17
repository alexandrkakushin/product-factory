package ru.pf.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pf.service.ThickClientService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация "агента" для удаленного использования платформы 1С:Предприятие
 * @author a.kakushin
 */
@RestController
@RequestMapping(path = "/api/agent")
class AgentController {

    @Autowired
    private ThickClientService thickClientService;

    @ApiOperation(value = "Получение списка доступных конфигураторов")
    @GetMapping("/thickclient/available")
    public List<String> thickClientAvailable() throws IOException {
        return thickClientService.versions().stream()
            .map(Path::toString)
            .collect(Collectors.toList());        
    }
}