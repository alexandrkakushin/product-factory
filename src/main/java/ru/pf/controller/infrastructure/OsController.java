package ru.pf.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.controller.ControllerUtil;
import ru.pf.controller.PfController;
import ru.pf.entity.Os;
import ru.pf.repository.OsRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(OsController.url)
public class OsController implements PfController {

    final static String url = "infrastructure/os";
    private final static String name = "Операционные системы";

    @Autowired
    private OsRepository osRepository;

    @Override
    public String getUrl() {
        return url;
    }

    @GetMapping
    public String items(Model model) {
        return ControllerUtil.items(model, name, osRepository.findAll(Sort.by("id")));
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("entity", new Os());
        return url + "/os-item";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("entity", osRepository.findById(id).orElse(new Os()));
        return url + "/os-item";
    }

    // todo: переделать на Async XHR
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        osRepository.deleteById(id);
        return "redirect:/" + url;
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Os entity) {
        Os saved = osRepository.save(entity);
        return "redirect:/" + url + "/" + saved.getId();
    }
}
