package ru.pf.utility;

import java.util.HashSet;
import java.util.Set;

/**
 * @author a.kakushin
 */
public class Properties {

    public static final String STORAGE = "STORAGE";
    public static final String CHECK_NAME_LENGTH = "CHECK_NAME_LENGTH";
    public static final String CHECK_LINE_SIZE = "CHECK_LINE_SIZE";


    public static Set<String> availables() {
        Set<String> result = new HashSet<>();
        result.add(STORAGE);
        result.add(CHECK_NAME_LENGTH);
        result.add(CHECK_LINE_SIZE);

        return result;
    }
}
