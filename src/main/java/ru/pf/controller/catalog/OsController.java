package ru.pf.controller.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Os;
import ru.pf.repository.OsRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(OsController.url)
public class OsController {

    final static String url = "catalogs/os";
    private final static String name = "Операционные системы";

    @Autowired
    OsRepository osRepository;

    @GetMapping
    public String osItems(Model model) {
        return ControllerUtil.items(model, url, name, osRepository.findAll(Sort.by("id")));
    }

    @GetMapping("/new")
    public String osFormNew(Model model) {
        model.addAttribute("entity", new Os());
        return url + "/os-item";
    }

    @GetMapping("/{id}")
    public String osForm(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("entity", osRepository.findById(id).orElse(new Os()));
        return url + "/os-item";
    }

    // todo: переделать на Async XHR
    @RequestMapping("/delete/{id}")
    public String osDelete(@PathVariable(name = "id") Long id) {
        osRepository.deleteById(id);
        return "redirect:/" + url;
    }

    @PostMapping("/submit")
    public String osSubmit(@ModelAttribute Os entity) {
        Os saved = osRepository.save(entity);
        return "redirect:/" + url + "/" + saved.getId();
    }
}
