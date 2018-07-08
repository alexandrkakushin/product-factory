package ru.pf.controller.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Os;
import ru.pf.repository.OsRepository;

/**
 * @author a.kakushin1
 */
@Controller
@RequestMapping("/catalogs/os")
public class OsController {

    @Autowired
    OsRepository osRepository;

    @GetMapping
    public String osItems(Model model) {
        model.addAttribute("items", osRepository.findAll(Sort.by("id")));
        return "catalogs/os/os-list";
    }

    @GetMapping("/new")
    public String osFormNew(Model model) {
        model.addAttribute("entity", new Os());
        return "catalogs/os/os-item";
    }

    @GetMapping("/{id}")
    public String osForm(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("entity", osRepository.findById(id).orElse(new Os()));
        return "catalogs/os/os-item";
    }

    // todo: переделать на Async XHR
    @RequestMapping("/delete/{id}")
    public String osDelete(@PathVariable(name = "id") Long id) {
        osRepository.deleteById(id);
        return "redirect:/catalogs/os";
    }

    @PostMapping("/submit")
    public String osSubmit(@ModelAttribute Os entity) {
        Os saved = osRepository.save(entity);
        return "redirect:/catalogs/os/" + saved.getId();
    }
}
