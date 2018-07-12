package ru.pf.controller.catalog;

import org.springframework.ui.Model;

/**
 * @author a.kakushin
 */
public class ControllerUtil {

    public static String items(Model model, String url, String name, Iterable<?> items) {
        model.addAttribute("url", url);
        model.addAttribute("name", name);
        model.addAttribute("items", items);
        return "catalogs/items";
    }
}
