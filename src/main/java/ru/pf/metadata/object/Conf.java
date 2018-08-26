package ru.pf.metadata.object;

import lombok.Data;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.common.CommonModule;
import ru.pf.metadata.object.common.Language;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Data
public class Conf extends AbstractObject<Conf> {

    private Module ordinaryApplicationModule;
    private Module managedApplicationModule;
    private Module sessionModule;
    private Module externalConnectionModule;

    private Set<CommonModule> commonModules;
    private Set<Language> languages = new HashSet<>();
    private Set<Constant> constants = new HashSet<>();
    private Set<Catalog> catalogs = new HashSet<>();
    private Set<Document> documents = new HashSet<>();
    private Set<Enum> enums = new HashSet<>();
    private Set<DataProcessor> dataProcessors = new HashSet<>();


    public Conf(Path path) {
        super(path);
        this.commonModules = new HashSet<>();
    }

    public Set<CommonModule> getCommonModules() {
        return commonModules;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public Set<Constant> getConstants() {
        return constants;
    }

    public Set<Catalog> getCatalogs() {
        return catalogs;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Set<Enum> getEnums() {
        return enums;
    }

    public Set<DataProcessor> getDataProcessors() {
        return dataProcessors;
    }

    @Override
    public void parse() {

    }
}
