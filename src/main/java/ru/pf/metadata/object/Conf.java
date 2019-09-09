package ru.pf.metadata.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.common.CommonModule;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;

/**
 * @author a.kakushin
 */
@Data
public class Conf extends AbstractMetadataObject {

    private Module ordinaryApplicationModule;
    private Module managedApplicationModule;
    private Module sessionModule;
    private Module externalConnectionModule;

    private Set<MetadataObject> subsystems;
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
    private Set<MetadataObject> documentJournals;
    private Set<MetadataObject> enums;
    private Set<MetadataObject> reports;
    private Set<MetadataObject> dataProcessors;
    private Set<MetadataObject> chartsOfCharacteristicTypes;
    private Set<MetadataObject> chartsOfAccounts;
    private Set<MetadataObject> chartsOfCalculationTypes;
    private Set<MetadataObject> informationRegisters;
    private Set<MetadataObject> accumulationRegisters;
    private Set<MetadataObject> accountingRegisters;
    private Set<MetadataObject> calculationRegisters;
    private Set<MetadataObject> businessProcesses;
    private Set<MetadataObject> tasks;
    private Set<MetadataObject> externalDataSources;

    public Conf(Path path) {
        super(path);

        this.subsystems = new LinkedHashSet<>();
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
        this.documentJournals = new LinkedHashSet<>();
        this.enums = new LinkedHashSet<>();
        this.reports = new LinkedHashSet<>();
        this.dataProcessors = new LinkedHashSet<>();
        this.chartsOfCharacteristicTypes = new LinkedHashSet<>();
        this.chartsOfAccounts = new LinkedHashSet<>();
        this.chartsOfCalculationTypes = new LinkedHashSet<>();
        this.informationRegisters = new LinkedHashSet<>();
        this.accumulationRegisters = new LinkedHashSet<>();
        this.accountingRegisters = new LinkedHashSet<>();
        this.calculationRegisters = new LinkedHashSet<>();
        this.businessProcesses = new LinkedHashSet<>();
        this.tasks = new LinkedHashSet<>();
        this.externalDataSources = new LinkedHashSet<>();
    }

    public Set<MetadataObject> getSubsystems() {
        return subsystems;
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

    public Set<MetadataObject> getDocumentJournals() {
        return documentJournals;
    }

    public Set<MetadataObject> getEnums() {
        return enums;
    }

    public Set<MetadataObject> getReports() {
        return reports;
    }

    public Set<MetadataObject> getDataProcessors() {
        return dataProcessors;
    }

    public Set<MetadataObject> getChartsOfCharacteristicTypes() {
        return chartsOfCharacteristicTypes;
    }

    public Set<MetadataObject> getChartsOfAccounts() {
        return chartsOfAccounts;
    }

    public Set<MetadataObject> getChartsOfCalculationTypes() {
        return chartsOfCalculationTypes;
    }

    public Set<MetadataObject> getInformationRegisters() {
        return informationRegisters;
    }

    public Set<MetadataObject> getAccumulationRegisters() {
        return accumulationRegisters;
    }

    public Set<MetadataObject> getAccountingRegisters() {
        return accountingRegisters;
    }

    public Set<MetadataObject> getCalculationRegisters() {
        return calculationRegisters;
    }

    public Set<MetadataObject> getBusinessProcesses() {
        return businessProcesses;
    }

    public Set<MetadataObject> getTasks() {
        return tasks;
    }

    public Set<MetadataObject> getExternalDataSources() {
        return externalDataSources;
    }

    @JsonIgnore
    public Set<MetadataObject> getAllObjects() {
        Set<MetadataObject> objects = new HashSet<>();

        for (java.lang.reflect.Method method : Conf.class.getMethods()) {
            if (method.getReturnType().equals(Set.class)
                    && !method.getName().equalsIgnoreCase("getAllObjects")) {
                try {
                    objects.addAll((Set<MetadataObject>) method.invoke(this));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return objects;
    }

    @JsonIgnore
    public Map<Module, MetadataObject> getAllModules() {
        Map<Module, MetadataObject> modules = new HashMap<>();

        // todo: streamAPI
        for (MetadataObject metadataObject : getAllObjects()) {
            for (Field field : metadataObject.getClass().getDeclaredFields()) {
                if (field.getType() == Module.class) {
                    field.setAccessible(true);
                    try {
                        Module module = (Module) field.get(metadataObject);
                        if (module != null) {
                            modules.put(module, metadataObject);
                        }
                    } catch (IllegalAccessException e) {
                        // todo
                        e.printStackTrace();
                    }
                }
            }
        }
        return modules;
    }

    public MetadataObject getObject(UUID uuid) {
        for (MetadataObject object : this.getAllObjects()) {
            if (((AbstractMetadataObject) object).getUuid().equals(uuid)) {
                return object;
            }
        }

        return null;
    }
}
