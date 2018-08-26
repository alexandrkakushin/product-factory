package ru.pf.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a.kakushin
 */
@Data
public  class Module {

    @JsonIgnore
    private Path file;

    private List<Method> methods;

    public Module() {
        this.methods = new ArrayList<>();
    }

    public Module(Path file) {
        this.file = file;
        this.methods = new ArrayList<>();
    }

    public List<Method> getMethods() {
        return methods;
    }
}
