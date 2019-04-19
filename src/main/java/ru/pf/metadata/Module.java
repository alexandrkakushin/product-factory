package ru.pf.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.pf.metadata.object.MetadataObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a.kakushin
 */
@Data
public class Module {

    @JsonView({MetadataJsonView.List.class, MetadataJsonView.Element.class})
    private Type type;

    @JsonIgnore
    private Path file;

    @JsonIgnore
    private String text;

    @JsonIgnore
    private boolean errors;

    private List<Method> methods;

    public Module() {
        this.methods = new ArrayList<>();
    }

    public Module(Path file) {
        this();
        this.file = file;
    }

    public Module(Path file, Type type) {
        this(file);
        this.type = type;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setErrors(boolean errors) {
        this.errors = errors;
    }

    public enum Type {
        MANAGER_MODULE,
        OBJECT_MODULE,
        COMMON_MODULE;
    }
}
