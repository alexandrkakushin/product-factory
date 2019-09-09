package ru.pf.metadata.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import ru.pf.metadata.object.AccountingRegister;
import ru.pf.metadata.object.AccumulationRegister;
import ru.pf.metadata.object.BusinessProcess;
import ru.pf.metadata.object.CalculationRegister;
import ru.pf.metadata.object.Catalog;
import ru.pf.metadata.object.ChartOfAccounts;
import ru.pf.metadata.object.ChartOfCalculationTypes;
import ru.pf.metadata.object.ChartOfCharacteristicTypes;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.Constant;
import ru.pf.metadata.object.DataProcessor;
import ru.pf.metadata.object.Document;
import ru.pf.metadata.object.DocumentJournal;
import ru.pf.metadata.object.ExternalDataSource;
import ru.pf.metadata.object.InformationRegister;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.object.Report;
import ru.pf.metadata.object.Task;
import ru.pf.metadata.object.common.CommandGroup;
import ru.pf.metadata.object.common.CommonAttribute;
import ru.pf.metadata.object.common.CommonCommand;
import ru.pf.metadata.object.common.CommonForm;
import ru.pf.metadata.object.common.CommonModule;
import ru.pf.metadata.object.common.CommonPicture;
import ru.pf.metadata.object.common.CommonTemplate;
import ru.pf.metadata.object.common.DefinedType;
import ru.pf.metadata.object.common.EventSubscription;
import ru.pf.metadata.object.common.ExchangePlan;
import ru.pf.metadata.object.common.FilterCriterion;
import ru.pf.metadata.object.common.FunctionalOption;
import ru.pf.metadata.object.common.FunctionalOptionsParameter;
import ru.pf.metadata.object.common.HttpService;
import ru.pf.metadata.object.common.Language;
import ru.pf.metadata.object.common.Role;
import ru.pf.metadata.object.common.ScheduledJob;
import ru.pf.metadata.object.common.SessionParameter;
import ru.pf.metadata.object.common.SettingsStorage;
import ru.pf.metadata.object.common.StyleItem;
import ru.pf.metadata.object.common.Subsystem;
import ru.pf.metadata.object.common.WebService;
import ru.pf.metadata.object.common.XdtoPackage;

/**
 * @author a.kakushin
 */
@Service
public class ConfReader {

    public Conf read(Path workPath) throws IOException {

        Conf conf;

        Path fileConfiguration = workPath.resolve("Configuration.xml");
        if (Files.exists(fileConfiguration)) {
            conf = new Conf(fileConfiguration);
        } else {
            throw new FileNotFoundException("File \"Configuration.xml\" not found'");
        }

        ObjectReader objReader = new ObjectReader(fileConfiguration);

        String nodeChildObjects = "/MetaDataObject/Configuration/ChildObjects/";

        for (Relation relation : getRelations(conf)) {      
            // При разборе XML-файла конфигурации имена узлов не соответсвуют
            // именам Java-классов 
            String nodeName = null;
            if (relation.getObjClass().equals(XdtoPackage.class)) {
                nodeName = "XDTOPackage";
            } else if (relation.getObjClass().equals(HttpService.class)) {
                nodeName = "HTTPService";
            } else {
                nodeName = relation.getObjClass().getSimpleName();
            }
            
            List<String> objectsName = objReader.readChild(nodeChildObjects + nodeName);
            for (String name : objectsName)
                try {
                    Path fileXml = workPath
                            .resolve(relation.getFile())
                            .resolve(name + ".xml");

                    Class<?> objClass = relation.getObjClass();

                    Constructor<?> cons = objClass.getConstructor(Path.class);
                    MetadataObject object = (MetadataObject) cons.newInstance(fileXml);
                    object.parse();

                    relation.getConf().add(object);

                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | ParserConfigurationException | InvocationTargetException | SAXException | XPathExpressionException e) {
                    e.printStackTrace();
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

        private Set<MetadataObject> conf;
        private Class<?> objClass;
        private String file;

        public Relation(Set<MetadataObject> conf, Class<?> objClass, String file) {
            this.conf = conf;
            this.objClass = objClass;
            this.file = file;
        }

        public Set<MetadataObject> getConf() {
            return this.conf;
        }

        public String getFile() {
            return this.file;
        }

        public Class<?> getObjClass() {
            return this.objClass;
        }
    }
}