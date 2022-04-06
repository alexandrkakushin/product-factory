package ru.pf.metadata.reader;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.Enum;
import ru.pf.metadata.object.*;
import ru.pf.metadata.object.common.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Service
public class ConfReader {

    public Conf read(Path workPath) throws ReaderException {

        Conf conf;

        Path fileConfiguration = workPath.resolve("Configuration.xml");
        if (Files.exists(fileConfiguration)) {
            conf = new Conf(fileConfiguration);
        } else {
            throw new ReaderException("File \"Configuration.xml\" not found'");
        }

        XmlReader xmlReader = new XmlReader(fileConfiguration);

        String nodeChildObjects = "/MetaDataObject/Configuration/ChildObjects/";

        for (Relation relation : getRelations(conf)) {      
            String refType = Utils.getRefType(relation.getObjClass());
            
            List<String> objectsName = xmlReader.readChild(nodeChildObjects + refType);
            for (String name : objectsName) {
                try {
                    Path fileXml = workPath
                            .resolve(relation.getFolder())
                            .resolve(name + ".xml");

                    Class<?> objClass = relation.getObjClass();

                    Constructor<?> cons = objClass.getConstructor(Path.class);
                    MetadataObject metadataObject = (MetadataObject) cons.newInstance(fileXml);
                    metadataObject.setConf(conf);

                    ObjectReader objReader = new ObjectReader(metadataObject);
                    objReader.read(true);

                    conf.putRef(refType, metadataObject);
                    relation.getContainer().add(metadataObject);

                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new ReaderException(e.getMessage());
                }
            }
        }

        return conf;
    }

    public static Set<Relation> getRelations(Conf conf) {
        Set<Relation> result = new HashSet<>();

        result.add(new Relation(
            conf.getCommonModules(), CommonModule.class, "CommonModules"));

        result.add(new Relation(
            conf.getSessionParameters(), SessionParameter.class, "SessionParameters"));

        result.add(new Relation(
            conf.getRoles(), Role.class, "Roles"));

        result.add(new Relation(
            conf.getCommonAttributes(), CommonAttribute.class, "CommonAttributes"));

        result.add(new Relation(
            conf.getExchangePlans(), ExchangePlan.class, "ExchangePlans"));

        result.add(new Relation(
            conf.getFilterCriteria(), FilterCriterion.class, "FilterCriteria"));

        result.add(new Relation(
            conf.getEventSubscriptions(), EventSubscription.class, "EventSubscriptions"));

        result.add(new Relation(
            conf.getScheduledJobs(), ScheduledJob.class, "ScheduledJobs"));

        result.add(new Relation(
            conf.getFunctionalOptions(), FunctionalOption.class, "FunctionalOptions"));

        result.add(new Relation(
            conf.getFunctionalOptionsParameters(), FunctionalOptionsParameter.class, "FunctionalOptionsParameters"));

        result.add(new Relation(
            conf.getDefinedTypes(), DefinedType.class, "DefinedTypes"));

        result.add(new Relation(
            conf.getSettingsStorages(), SettingsStorage.class, "SettingsStorages"));

        result.add(new Relation(
            conf.getCommonForms(), CommonForm.class, "CommonForms"));

        result.add(new Relation(
            conf.getCommonCommands(), CommonCommand.class, "CommonCommands"));

        result.add(new Relation(
            conf.getCommandGroups(), CommandGroup.class, "CommandGroups"));

        result.add(new Relation(
            conf.getCommonTemplates(), CommonTemplate.class, "CommonTemplates"));

        result.add(new Relation(
            conf.getCommonPictures(), CommonPicture.class, "CommonPictures"));

        result.add(new Relation(
            conf.getXdtoPackages(), XdtoPackage.class, "XDTOPackages"));

        result.add(new Relation(
            conf.getWebServices(), WebService.class, "WebServices"));

        result.add(new Relation(
            conf.getHttpServices(), HttpService.class, "HTTPServices"));            

        // TODO: WS-ссылки

        result.add(new Relation(
            conf.getStyleItems(), StyleItem.class, "StyleItems"));

        result.add(new Relation(
            conf.getLanguages(), Language.class, "Languages"));

        result.add(new Relation(
            conf.getConstants(), Constant.class, "Constants"));

        result.add(new Relation(
            conf.getCatalogs(), Catalog.class, "Catalogs"));

        result.add(new Relation(
            conf.getDocuments(), Document.class, "Documents"));

        result.add(new Relation(
            conf.getDocumentJournals(), DocumentJournal.class, "DocumentJournals"));

        result.add(new Relation(
            conf.getEnums(), Enum.class, "Enums"));

        result.add(new Relation(
            conf.getReports(), Report.class, "Reports"));

        result.add(new Relation(
            conf.getDataProcessors(), DataProcessor.class, "DataProcessors"));

        result.add(new Relation(
            conf.getChartsOfCharacteristicTypes(), ChartOfCharacteristicTypes.class, "ChartsOfCharacteristicTypes"));

        result.add(new Relation(
            conf.getChartsOfAccounts(), ChartOfAccounts.class, "ChartsOfAccounts"));

        result.add(new Relation(
            conf.getChartsOfCalculationTypes(), ChartOfCalculationTypes.class, "ChartsOfCalculationTypes"));

        result.add(new Relation(
            conf.getInformationRegisters(), InformationRegister.class, "InformationRegisters"));

        result.add(new Relation(
            conf.getAccumulationRegisters(), AccumulationRegister.class, "AccumulationRegisters"));

        result.add(new Relation(
            conf.getAccountingRegisters(), AccountingRegister.class, "AccountingRegisters"));

        result.add(new Relation(
            conf.getCalculationRegisters(), CalculationRegister.class, "CalculationRegisters"));

        result.add(new Relation(
            conf.getBusinessProcesses(), BusinessProcess.class, "BusinessProcesses"));

        result.add(new Relation(
            conf.getTasks(), Task.class, "Tasks"));

        result.add(new Relation(
            conf.getExternalDataSources(), ExternalDataSource.class, "ExternalDataSources"));            

        result.add(new Relation(conf.getSubsystems(), Subsystem.class, "Subsystems"));

        return result;
    }

    public static class Relation {

        private final Set<IMetadataObject> container;
        private final Class<?> objClass;
        private final String folder;

        public Relation(Set<IMetadataObject> conf, Class<?> objClass, String folder) {
            this.container = conf;
            this.objClass = objClass;
            this.folder = folder;
        }

        public Set<IMetadataObject> getContainer() {
            return this.container;
        }

        public String getFolder() {
            return this.folder;
        }

        public Class<?> getObjClass() {
            return this.objClass;
        }
    }
}