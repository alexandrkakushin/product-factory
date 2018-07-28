package ru.pf.controller.vcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.controller.ControllerUtil;
import ru.pf.controller.PfController;
import ru.pf.entity.Cr;
import ru.pf.repository.CrRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(CrController.url)
public class CrController implements PfController {

    final static String url = "vcs/cr";
    private final static String name = "Хранилища конфигураций";

    @Autowired
    private CrRepository crRepository;

    @Override
    public String getUrl() {
        return url;
    }

    @GetMapping
    public String items(Model model) {
        return ControllerUtil.items(model, name, crRepository.findAll(Sort.by("id")));
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("entity", new Cr());
        return url + "/cr-item";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("entity", crRepository.findById(id).orElse(new Cr()));
        return url + "/cr-item";
    }

    // todo: переделать на Async XHR
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        crRepository.deleteById(id);
        return "redirect:/" + url;
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Cr entity) {
        Cr saved = crRepository.save(entity);
        return "redirect:/" + url + "/" + saved.getId();
    }
}
