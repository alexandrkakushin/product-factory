package ru.pf.metadata.object.common;

import lombok.Data;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.reader.ModuleReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
public class CommonModule extends AbstractObject<CommonModule> {

    private Module module;

    public CommonModule(Path path) {
        super(path);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void parse() throws IOException {
        Path fileModule = super.getFile()
                .getParent()
                .resolve(this.getShortName(super.getFile()))
                .resolve("Ext")
                .resolve("Module.bsl");

        if (Files.exists(fileModule)) {
            this.module = ModuleReader.read(fileModule);
        }
    }
}
