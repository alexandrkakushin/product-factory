package ru.pf.metadata;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author a.kakushin
 */
@Data
public class Method {

    @JsonView({MetadataJsonView.List.class, MetadataJsonView.Element.class})
    private String name;
    private Type type;
    private boolean isExport;
    private List<Arg> args;
    private String text;

    public Method() {
        this.args = new ArrayList<>();
    }

    public Method(String text) {
        this();
        this.text = text;
    }

    public List<Arg> getArgs() {
        return args;
    }

    public String getText() {
        return this.text;
    }

    public String getCode() {
        StringBuilder sb = new StringBuilder();

        Arrays.asList(this.getText().split(System.lineSeparator()))
                .stream()
                .filter(line -> !line.trim().startsWith("//"))
                .forEach(s -> sb.append(s).append(System.lineSeparator()));

        return sb.toString();
    }

    public boolean isFunction() {
        return this.type == Type.FUNCTION;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEmpty() {
        boolean result = true;
        String[] parts = this.text.split(System.getProperty("line.separator"));
        for (String part : parts) {
            String doProcess = part.trim().toLowerCase();
            if (doProcess.isEmpty()) {
                continue;
            }

            if (doProcess.startsWith("функция") || doProcess.startsWith("процедура")) {
                continue;
            }

            if (doProcess.startsWith("конецфункции") || doProcess.startsWith("конецпроцедуры")) {
                continue;
            }

            if (doProcess.startsWith("//")) {
                continue;
            }

            result = false;
        }
        return result;
    }

    @Data
    public static class Arg {
        private String name;
        private String defaultValue;

        public Arg() {
        }

        public Arg(String name, String defaultValue) {
            this.name = name;
            this.defaultValue = defaultValue;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public enum Type {
        FUNCTION,
        PROCEDURE
    }
}
