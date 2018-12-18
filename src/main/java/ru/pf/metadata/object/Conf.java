package ru.pf.metadata.object;

import lombok.Data;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.common.CommonModule;
import ru.pf.metadata.object.common.Language;

import java.nio.file.Path;
import java.util.*;

/**
 * @author a.kakushin
 */
@Data
public class Conf extends AbstractObject<Conf> {

    private Module ordinaryApplicationModule;
    private Module managedApplicationModule;
    private Module sessionModule;
    private Module externalConnectionModule;

    private Set<MetadataObject> commonModules;
    private Set<MetadataObject> sessionParameters;

    private Set<MetadataObject> languages;
    private Set<MetadataObject> constants;
    private Set<MetadataObject> catalogs;
    private Set<MetadataObject> documents;
    private Set<MetadataObject> enums;
    private Set<MetadataObject> dataProcessors;


    public Conf(Path path) {
        super(path);

        this.commonModules = new LinkedHashSet<>();
        this.sessionParameters = new LinkedHashSet<>();
        this.languages = new LinkedHashSet<>();
        this.constants = new LinkedHashSet<>();
        this.catalogs = new LinkedHashSet<>();
        this.documents = new LinkedHashSet<>();
        this.enums = new LinkedHashSet<>();
        this.dataProcessors = new LinkedHashSet<>();
    }

    public Set<MetadataObject> getCommonModules() {
        return commonModules;
    }

    public Set<MetadataObject> getSessionParameters() {
        return sessionParameters;
    }

    public Set<MetadataObject> getLanguages() {
        return languages;
    }

    public Set<MetadataObject> getConstants() {
        return constants;
    }

    public Set<MetadataObject> getCatalogs() {
        return catalogs;
    }

    public Set<MetadataObject> getDocuments() {
        return documents;
    }

    public Set<MetadataObject> getEnums() {
        return enums;
    }

    public Set<MetadataObject> getDataProcessors() {
        return dataProcessors;
    }

    @Override
    public void parse() {

    }
}
