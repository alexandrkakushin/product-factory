package ru.pf.controller;

import org.springframework.ui.Model;

/**
 * @author a.kakushin
 */
public class ControllerUtil {

    public static String items(Model model, String name, Iterable<?> items) {
        model.addAttribute("name", name);
        model.addAttribute("items", items);
        return "catalogs/items";
    }
}
