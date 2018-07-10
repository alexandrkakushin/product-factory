package ru.pf.controller.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Cr;
import ru.pf.repository.CrRepository;

/**
 * @author a.kakushin1
 */
@Controller
@RequestMapping("/catalogs/cr")
public class CrController {

    @Autowired
    CrRepository crRepository;

    @GetMapping
    public String osItems(Model model) {
        model.addAttribute("items", crRepository.findAll(Sort.by("id")));
        return "catalogs/cr/cr-list";
    }

    @GetMapping("/new")
    public String osFormNew(Model model) {
        model.addAttribute("entity", new Cr());
        return "catalogs/cr/cr-item";
    }

    @GetMapping("/{id}")
    public String osForm(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("entity", crRepository.findById(id).orElse(new Cr()));
        return "catalogs/cr/cr-item";
    }

    // todo: переделать на Async XHR
    @RequestMapping("/delete/{id}")
    public String osDelete(@PathVariable(name = "id") Long id) {
        crRepository.deleteById(id);
        return "redirect:/catalogs/cr";
    }

    @PostMapping("/submit")
    public String osSubmit(@ModelAttribute Cr entity) {
        Cr saved = crRepository.save(entity);
        return "redirect:/catalogs/cr/" + saved.getId();
    }
}
