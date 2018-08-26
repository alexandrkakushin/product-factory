package ru.pf.metadata;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.kakushin
 */
@Data
public class Method {

    private String name;
    private Type type;
    private boolean isExport;
    private List<Arg> args;

    public Method() {
        this.args = new ArrayList<>();
    }

    public Method(Type type, String name, boolean isExport) {
        this();
        this.name = name;
        this.type = type;
        this.isExport = isExport;
    }

    public List<Arg> getArgs() {
        return args;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setExport(boolean export) {
        isExport = export;
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
