package ru.pf.controller.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.Cr;
import ru.pf.repository.CrRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(CrController.url)
public class CrController {

    final static String url = "catalogs/cr";
    private final static String name = "Хранилища конфигураций";

    @Autowired
    CrRepository crRepository;

    @GetMapping
    public String osItems(Model model) {
        return ControllerUtil.items(model, url, name, crRepository.findAll(Sort.by("id")));
    }

    @GetMapping("/new")
    public String osFormNew(Model model) {
        model.addAttribute("entity", new Cr());
        return url + "/cr-item";
    }

    @GetMapping("/{id}")
    public String osForm(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("entity", crRepository.findById(id).orElse(new Cr()));
        return url + "/cr-item";
    }

    // todo: переделать на Async XHR
    @RequestMapping("/delete/{id}")
    public String osDelete(@PathVariable(name = "id") Long id) {
        crRepository.deleteById(id);
        return "redirect:/" + url;
    }

    @PostMapping("/submit")
    public String osSubmit(@ModelAttribute Cr entity) {
        Cr saved = crRepository.save(entity);
        return "redirect:/" + url + "/" + saved.getId();
    }
}
