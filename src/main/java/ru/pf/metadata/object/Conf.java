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
    private Set<MetadataObject> roles;
    private Set<MetadataObject> commonAttributes;
    private Set<MetadataObject> exchangePlans;
    private Set<MetadataObject> filterCriteria;
    private Set<MetadataObject> eventSubscriptions;
    private Set<MetadataObject> scheduledJobs;
    private Set<MetadataObject> functionalOptions;
    private Set<MetadataObject> functionalOptionsParameters;
    private Set<MetadataObject> definedTypes;
    private Set<MetadataObject> settingsStorages;
    private Set<MetadataObject> commonForms;
    private Set<MetadataObject> commonCommands;
    private Set<MetadataObject> commandGroups;
    private Set<MetadataObject> commonTemplates;
    private Set<MetadataObject> commonPictures;
    private Set<MetadataObject> xdtoPackages;
    private Set<MetadataObject> webServices;
    private Set<MetadataObject> httpServices;
    private Set<MetadataObject> styleItems;

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
        this.roles = new LinkedHashSet<>();
        this.commonAttributes = new LinkedHashSet<>();
        this.exchangePlans = new LinkedHashSet<>();
        this.filterCriteria = new LinkedHashSet<>();
        this.eventSubscriptions = new LinkedHashSet<>();
        this.scheduledJobs = new LinkedHashSet<>();
        this.functionalOptions = new LinkedHashSet<>();
        this.functionalOptionsParameters = new LinkedHashSet<>();
        this.definedTypes = new LinkedHashSet<>();
        this.settingsStorages = new LinkedHashSet<>();
        this.commonForms = new LinkedHashSet<>();
        this.commonCommands = new LinkedHashSet<>();
        this.commandGroups = new LinkedHashSet<>();
        this.commonTemplates = new LinkedHashSet<>();
        this.commonPictures = new LinkedHashSet<>();
        this.xdtoPackages = new LinkedHashSet<>();
        this.webServices = new LinkedHashSet<>();
        this.httpServices = new LinkedHashSet<>();
        this.styleItems = new LinkedHashSet<>();

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

    public Set<MetadataObject> getRoles() {
        return roles;
    }

    public Set<MetadataObject> getCommonAttributes() {
        return commonAttributes;
    }

    public Set<MetadataObject> getExchangePlans() {
        return exchangePlans;
    }

    public Set<MetadataObject> getFilterCriteria() {
        return filterCriteria;
    }

    public Set<MetadataObject> getEventSubscriptions() {
        return eventSubscriptions;
    }

    public Set<MetadataObject> getScheduledJobs() {
        return scheduledJobs;
    }

    public Set<MetadataObject> getFunctionalOptions() {
        return functionalOptions;
    }

    public Set<MetadataObject> getFunctionalOptionsParameters() {
        return functionalOptionsParameters;
    }

    public Set<MetadataObject> getDefinedTypes() {
        return definedTypes;
    }

    public Set<MetadataObject> getSettingsStorages() {
        return settingsStorages;
    }

    public Set<MetadataObject> getCommonForms() {
        return commonForms;
    }

    public Set<MetadataObject> getCommonCommands() {
        return commonCommands;
    }

    public Set<MetadataObject> getCommandGroups() {
        return commandGroups;
    }

    public Set<MetadataObject> getCommonTemplates() {
        return commonTemplates;
    }

    public Set<MetadataObject> getCommonPictures() {
        return commonPictures;
    }

    public Set<MetadataObject> getXdtoPackages() {
        return xdtoPackages;
    }

    public Set<MetadataObject> getWebServices() {
        return webServices;
    }

    public Set<MetadataObject> getHttpServices() {
        return httpServices;
    }

    public Set<MetadataObject> getStyleItems() {
        return styleItems;
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
