package ru.pf.metadata.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.pf.metadata.Module;

/**
 * @author a.kakushin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Conf extends MetadataObject {

    private Module ordinaryApplicationModule;
    private Module managedApplicationModule;
    private Module sessionModule;
    private Module externalConnectionModule;

    private Set<IMetadataObject> subsystems;
    private Set<IMetadataObject> commonModules;
    private Set<IMetadataObject> sessionParameters;
    private Set<IMetadataObject> roles;
    private Set<IMetadataObject> commonAttributes;
    private Set<IMetadataObject> exchangePlans;
    private Set<IMetadataObject> filterCriteria;
    private Set<IMetadataObject> eventSubscriptions;
    private Set<IMetadataObject> scheduledJobs;
    private Set<IMetadataObject> functionalOptions;
    private Set<IMetadataObject> functionalOptionsParameters;
    private Set<IMetadataObject> definedTypes;
    private Set<IMetadataObject> settingsStorages;
    private Set<IMetadataObject> commonForms;
    private Set<IMetadataObject> commonCommands;
    private Set<IMetadataObject> commandGroups;
    private Set<IMetadataObject> commonTemplates;
    private Set<IMetadataObject> commonPictures;
    private Set<IMetadataObject> xdtoPackages;
    private Set<IMetadataObject> webServices;
    private Set<IMetadataObject> httpServices;
    private Set<IMetadataObject> styleItems;

    private Set<IMetadataObject> languages;
    private Set<IMetadataObject> constants;
    private Set<IMetadataObject> catalogs;
    private Set<IMetadataObject> documents;
    private Set<IMetadataObject> documentJournals;
    private Set<IMetadataObject> enums;
    private Set<IMetadataObject> reports;
    private Set<IMetadataObject> dataProcessors;
    private Set<IMetadataObject> chartsOfCharacteristicTypes;
    private Set<IMetadataObject> chartsOfAccounts;
    private Set<IMetadataObject> chartsOfCalculationTypes;
    private Set<IMetadataObject> informationRegisters;
    private Set<IMetadataObject> accumulationRegisters;
    private Set<IMetadataObject> accountingRegisters;
    private Set<IMetadataObject> calculationRegisters;
    private Set<IMetadataObject> businessProcesses;
    private Set<IMetadataObject> tasks;
    private Set<IMetadataObject> externalDataSources;

    private Map<String, UUID> refs;

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

        this.refs = new HashMap<>();
    }

    public Set<IMetadataObject> getSubsystems() {
        return subsystems;
    }

    public Set<IMetadataObject> getCommonModules() {
        return commonModules;
    }

    public Set<IMetadataObject> getSessionParameters() {
        return sessionParameters;
    }

    public Set<IMetadataObject> getRoles() {
        return roles;
    }

    public Set<IMetadataObject> getCommonAttributes() {
        return commonAttributes;
    }

    public Set<IMetadataObject> getExchangePlans() {
        return exchangePlans;
    }

    public Set<IMetadataObject> getFilterCriteria() {
        return filterCriteria;
    }

    public Set<IMetadataObject> getEventSubscriptions() {
        return eventSubscriptions;
    }

    public Set<IMetadataObject> getScheduledJobs() {
        return scheduledJobs;
    }

    public Set<IMetadataObject> getFunctionalOptions() {
        return functionalOptions;
    }

    public Set<IMetadataObject> getFunctionalOptionsParameters() {
        return functionalOptionsParameters;
    }

    public Set<IMetadataObject> getDefinedTypes() {
        return definedTypes;
    }

    public Set<IMetadataObject> getSettingsStorages() {
        return settingsStorages;
    }

    public Set<IMetadataObject> getCommonForms() {
        return commonForms;
    }

    public Set<IMetadataObject> getCommonCommands() {
        return commonCommands;
    }

    public Set<IMetadataObject> getCommandGroups() {
        return commandGroups;
    }

    public Set<IMetadataObject> getCommonTemplates() {
        return commonTemplates;
    }

    public Set<IMetadataObject> getCommonPictures() {
        return commonPictures;
    }

    public Set<IMetadataObject> getXdtoPackages() {
        return xdtoPackages;
    }

    public Set<IMetadataObject> getWebServices() {
        return webServices;
    }

    public Set<IMetadataObject> getHttpServices() {
        return httpServices;
    }

    public Set<IMetadataObject> getStyleItems() {
        return styleItems;
    }

    public Set<IMetadataObject> getLanguages() {
        return languages;
    }

    public Set<IMetadataObject> getConstants() {
        return constants;
    }

    public Set<IMetadataObject> getCatalogs() {
        return catalogs;
    }

    public Set<IMetadataObject> getDocuments() {
        return documents;
    }

    public Set<IMetadataObject> getDocumentJournals() {
        return documentJournals;
    }

    public Set<IMetadataObject> getEnums() {
        return enums;
    }

    public Set<IMetadataObject> getReports() {
        return reports;
    }

    public Set<IMetadataObject> getDataProcessors() {
        return dataProcessors;
    }

    public Set<IMetadataObject> getChartsOfCharacteristicTypes() {
        return chartsOfCharacteristicTypes;
    }

    public Set<IMetadataObject> getChartsOfAccounts() {
        return chartsOfAccounts;
    }

    public Set<IMetadataObject> getChartsOfCalculationTypes() {
        return chartsOfCalculationTypes;
    }

    public Set<IMetadataObject> getInformationRegisters() {
        return informationRegisters;
    }

    public Set<IMetadataObject> getAccumulationRegisters() {
        return accumulationRegisters;
    }

    public Set<IMetadataObject> getAccountingRegisters() {
        return accountingRegisters;
    }

    public Set<IMetadataObject> getCalculationRegisters() {
        return calculationRegisters;
    }

    public Set<IMetadataObject> getBusinessProcesses() {
        return businessProcesses;
    }

    public Set<IMetadataObject> getTasks() {
        return tasks;
    }

    public Set<IMetadataObject> getExternalDataSources() {
        return externalDataSources;
    }

    @JsonIgnore
    public Set<IMetadataObject> getAllObjects() {
        Set<IMetadataObject> objects = new HashSet<>();

        for (java.lang.reflect.Method method : Conf.class.getMethods()) {
            if (method.getReturnType().equals(Set.class)
                    && !method.getName().equalsIgnoreCase("getAllObjects")) {
                try {
                    objects.addAll((Set<IMetadataObject>) method.invoke(this));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return objects;
    }

    @JsonIgnore
    public Map<Module, IMetadataObject> getAllModules() {
        Map<Module, IMetadataObject> modules = new HashMap<>();

        // todo: streamAPI
        for (IMetadataObject metadataObject : getAllObjects()) {
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

    public IMetadataObject getObject(UUID uuid) {
        for (IMetadataObject object : this.getAllObjects()) {
            if (((MetadataObject) object).getUuid().equals(uuid)) {
                return object;
            }
        }
        return null;
    }

    public Map<String, UUID> getRefs() {
        return this.refs;
    }

    public void putRef(String ref, UUID uuid) {
        this.refs.put(ref, uuid);
    }

    public void putRef(String refType, MetadataObject metadataObject) {
        putRef(
                refType + "." + metadataObject.getName(),
                metadataObject.getUuid()
        );
    }

    public UUID getUuidByRef(String ref) {
        return this.refs.get(ref);
    }

    public IMetadataObject getObjectByRef(String ref) {
        IMetadataObject result = null;
        UUID uuid = getUuidByRef(ref);
        if (uuid != null) {
            result = getObject(uuid);
        }
        return result;
    }
}
