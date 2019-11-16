package ru.pf.controller.crud;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.pf.controller.PfController;
import ru.pf.entity.PfEntity;
import ru.pf.repository.PfRepository;

/**
 * Интерфейс контроллеров для реализация CRUD-операций
 * @author a.kakushin
 */
@Controller
public interface PfCrudController<T extends PfEntity<?>> extends PfController {

    String getTemplateItem();

    default String getTemplateList() {
        return "catalogs/items";
    }

    PfRepository<T> getRepository();

    default void addAttributesItems(Model model) {}

    default void addAttributesItem(Model model) {}

    @GetMapping
    default String items(Model model) {
        model.addAttribute("name", getName());
        model.addAttribute("items", getRepository().findAll(Sort.by("id")));
        addAttributesItems(model);

        return getTemplateList();
    }

    @GetMapping("/new")
    default String formNew(Model model) {
        model.addAttribute("entity", getRepository().newInstance());
        addAttributesItem(model);

        return getUrl() + "/" + getTemplateItem();
    }

    @GetMapping("/{id}")
    default String form(@PathVariable(name = "id") Long id, Model model) {        
        T entity =  getRepository().findById(id)
                .orElse(getRepository().newInstance());

        model.addAttribute("entity", entity);
        addAttributesItem(model);

        return getUrl() + "/" + getTemplateItem();
    }

    @PostMapping("/submit")
    default String submit(@ModelAttribute T entity) {
        T saved = getRepository().save(entity);
        return "redirect:/" + getUrl() + "/" + saved.getId();
    }

    @RequestMapping("/delete/{id}")
    default String delete(@PathVariable(name = "id") Long id) {
        // TODO: переделать на Async XHR
        getRepository().deleteById(id);
        return "redirect:/" + getUrl();
    }
}
