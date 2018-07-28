package ru.pf.controller.development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pf.controller.ControllerUtil;
import ru.pf.controller.PfController;
import ru.pf.entity.Project;
import ru.pf.repository.CrRepository;
import ru.pf.repository.ProjectsRepository;

/**
 * @author a.kakushin
 */
@Controller
@RequestMapping(ProjectsController.url)
public class ProjectsController implements PfController {

    final static String url = "development/projects";
    private final static String name = "Проекты";

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private CrRepository crRepository;

    @Override
    public String getUrl() {
        return url;
    }

    @GetMapping
    public String items(Model model) {
        return ControllerUtil.items(model, name, projectsRepository.findAll(Sort.by("id")));
    }

    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("entity", new Project());
        model.addAttribute("crList", crRepository.findAll(Sort.by("id")));
        return url + "/project-item";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable(name = "id") Long id, Model model) {
        Project project = projectsRepository.findById(id).get();
        model.addAttribute("entity", project);
        model.addAttribute("crList", crRepository.findAll(Sort.by("id")));
        return url + "/project-item";
    }

    // todo: переделать на Async XHR
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        projectsRepository.deleteById(id);
        return "redirect:/" + url;
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Project entity) {
        if (entity.getCr() != null) {
            if (entity.getCr().getId() == null) {
                entity.setCr(null);
            }
        }
        Project saved = projectsRepository.save(entity);
        return "redirect:/" + url + "/" + saved.getId();
    }
}
