package ru.pf.controller.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.pf.service.ThickClientService;

/**
 * Реализация "агента" для
 * @author a.kakushin
 */
@RestController
@RequestMapping(path = "/api/agent")
class AgentController {

    @Autowired
    private ThickClientService thickClientService;

    @GetMapping("/thickclient/available")
    public List<String> thickClientAvailable() throws IOException {
        return thickClientService.versions().stream()
            .map(item -> item.toString())
            .collect(Collectors.toList());        
    }
}