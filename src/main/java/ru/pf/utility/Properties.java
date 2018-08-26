package ru.pf.utility;

import java.util.HashSet;
import java.util.Set;

/**
 * @author a.kakushin
 */
public class Properties {

    public static final String STORAGE = "STORAGE";

    public static Set<String> availables() {
        Set<String> result = new HashSet<>();
        result.add(STORAGE);

        return result;
    }
}
