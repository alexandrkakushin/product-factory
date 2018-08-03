package ru.pf.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.entity.PfEntity;
import ru.pf.repository.PfRepository;

import java.util.Map;

/**
 * Интерфейс контроллеров для реализация CRUD-операций
 * @author a.kakushin
 */
@Controller
public interface PfController<T extends PfEntity, ID> {

    String getUrl();

    String getTemplateItem();

    String getName();

    PfRepository<T, ID> getRepository();

    default void addAttributesItems(Model model) {}

    default void addAttributesItem(Model model) {}

    @GetMapping
    default String items(Model model) {
        model.addAttribute("name", getName());
        model.addAttribute("items", getRepository().findAll(Sort.by("id")));
        addAttributesItems(model);

        return "catalogs/items";
    }

    @GetMapping("/new")
    default String formNew(Model model) {
        model.addAttribute("entity", getRepository().newInstance());
        addAttributesItem(model);

        return getUrl() + "/" + getTemplateItem();
    }

    @GetMapping("/{id}")
    default String form(@PathVariable(name = "id") Long id, Model model) {
        T entity =  getRepository().findById((ID) id)
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
    default String delete(@PathVariable(name = "id") ID id) {
        // todo: переделать на Async XHR
        getRepository().deleteById(id);
        return "redirect:/" + getUrl();
    }
}
