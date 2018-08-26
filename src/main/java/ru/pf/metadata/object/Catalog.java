package ru.pf.metadata.object;

import lombok.Data;
import ru.pf.metadata.Module;
import ru.pf.metadata.reader.ModuleReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Data
public class Catalog extends AbstractObject<Catalog> {

    private Module managerModule;
    private Module objectModule;

    public Catalog(Path path) {
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
        Path fileManagerModule = super.getFile()
                .getParent()
                .resolve(this.getShortName(super.getFile()))
                .resolve("Ext")
                .resolve("ManagerModule.bsl");

        if (Files.exists(fileManagerModule)) {
            this.managerModule = ModuleReader.read(fileManagerModule);
        }

        Path fileObjectModule = super.getFile()
                .getParent()
                .resolve(this.getShortName(super.getFile()))
                .resolve("Ext")
                .resolve("ObjectModule.bsl");

        if (Files.exists(fileObjectModule)) {
            this.objectModule = ModuleReader.read(fileObjectModule);
        }
    }
}
