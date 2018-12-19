package ru.pf.metadata.reader;

import org.springframework.stereotype.Service;
import org.w3c.dom.html.HTMLParagraphElement;
import org.xml.sax.SAXException;
import ru.pf.metadata.object.*;
import ru.pf.metadata.object.Enum;
import ru.pf.metadata.object.common.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        Map<Class, Container> description = getDescription(conf);
        for (Class objClass : description.keySet()) {
            String nodeMetadata = nodeChildObjects + AbstractObject.getMetadataName(objClass);
            List<String> objectsName = objReader.readChild(nodeMetadata);

            Container container = description.get(objClass);
            if (container == null) {
                // todo: проработать исключения
                throw new IOException("Не определен контейнер для записи объекта");
            }

            for (String name : objectsName)
                try {
                    Path fileXml = workPath
                            .resolve(container.getFile())
                            .resolve(name + ".xml");

                    Constructor<?> cons = objClass.getConstructor(Path.class);
                    MetadataObject object = (MetadataObject) cons.newInstance(fileXml);
                    object.parse();

                    container.getConf().add(object);

                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | ParserConfigurationException | InvocationTargetException | SAXException | XPathExpressionException e) {
                    e.printStackTrace();
                }
        }

        return conf;
    }

    private Map<Class, Container> getDescription(Conf conf) {
        Map<Class, Container> result = new HashMap<>();

    /*Общие
         Подсистемы*/
        result.put(CommonModule.class, new Container(conf.getCommonModules(), "CommonModules"));
        result.put(SessionParameter.class, new Container(conf.getSessionParameters(), "SessionParameters"));
        result.put(Role.class, new Container(conf.getRoles(), "Roles"));
        result.put(CommonAttribute.class, new Container(conf.getCommonAttributes(), "CommonAttributes"));
        result.put(ExchangePlan.class, new Container(conf.getExchangePlans(), "ExchangePlans"));
        result.put(FilterCriterion.class, new Container(conf.getFilterCriteria(), "FilterCriteria"));
        result.put(EventSubscription.class, new Container(conf.getEventSubscriptions(), "EventSubscriptions"));
        result.put(ScheduledJob.class, new Container(conf.getScheduledJobs(), "ScheduledJobs"));
        result.put(FunctionalOption.class, new Container(conf.getFunctionalOptions(), "FunctionalOptions"));
        result.put(FunctionalOptionsParameter.class, new Container(conf.getFunctionalOptionsParameters(), "FunctionalOptionsParameters"));
        result.put(DefinedType.class, new Container(conf.getDefinedTypes(), "DefinedTypes"));
        result.put(SettingsStorage.class, new Container(conf.getSettingsStorages(), "SettingsStorages"));
        result.put(CommonForm.class, new Container(conf.getCommonForms(), "CommonForms"));
        result.put(CommonCommand.class, new Container(conf.getCommonCommands(), "CommonCommands"));
        result.put(CommandGroup.class, new Container(conf.getCommandGroups(), "CommandGroups"));
        result.put(CommonTemplate.class, new Container(conf.getCommonTemplates(), "CommonTemplates"));
        result.put(CommonPicture.class, new Container(conf.getCommonPictures(), "CommonPictures"));
        result.put(XdtoPackage.class, new Container(conf.getXdtoPackages(), "XDTOPackages"));
        result.put(WebService.class, new Container(conf.getWebServices(), "WebServices"));
        result.put(HttpService.class, new Container(conf.getHttpServices(), "HTTPServices"));

        //WS-ссылки
        result.put(StyleItem.class, new Container(conf.getStyleItems(), "StyleItems"));
        result.put(Language.class, new Container(conf.getLanguages(), "Languages"));

        //result.put(Constant.class, new Container(conf.getConstants(), "Constants"));
        //result.put(Catalog.class, new Container(conf.getCatalogs(), "Catalogs"));
        // Документы
        // Журналы документов
        //result.put(Enum.class, new Container(conf.getEnums(), "Enums"));
        // Отчеты
        //result.put(DataProcessor.class, new Container(conf.getDataProcessors(), "DataProcessors"));

         /*Планы видов характеристик
         Планы счетов
         Планы видов расчета
         Регистры сведений
         Регистры накопления
         Регистры бухгалтерии
         Регистры расчета
         Бизнес-процессы
         Задачи
         Внешние источники данных*/

        return result;
    }

    private static class Container {

        private Set<MetadataObject> conf;
        private String file;

        public Container(Set<MetadataObject> conf, String file) {
            this.conf = conf;
            this.file = file;
        }

        public Set<MetadataObject> getConf() {
            return conf;
        }

        public String getFile() {
            return file;
        }
    }
}
