package ru.pf.licence.solution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import ru.pf.entity.InfoBase;
import ru.pf.entity.Project;
import ru.pf.entity.licence.LicenceBuildScript;
import ru.pf.licence.BatchModeLicence;
import ru.pf.licence.LicenceException;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.reader.ConfReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.service.ProjectsService;
import ru.pf.service.PropertiesService;
import ru.pf.yellow.BatchModeYellow;
import ru.pf.yellow.Extension;
import ru.pf.yellow.Yellow;
import ru.pf.yellow.YellowException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис для создания защищенных решений (СЛК)
 *
 * Цель заключается в хранении исходных кодов (обработка) в самой конфигурации.
 * При сборке защищенного решения формируется защищенный модуль, вставляется в проект, а обработка удаляется
 */
@Service
public class LicenceSolutionGenerator {

    /**
     * Пакетный режим 1С:Предприятие
     */
    @Autowired
    private BatchModeYellow batchModeYellow;

    /**
     * Пакетный режим утилиты лицензирования СЛК
     */
    @Autowired
    private BatchModeLicence batchModeLicence;

    /**
     * Сервис для управления проектами
     */
    @Autowired
    private ProjectsService projectsService;

    /**
     * Сервис параметров приложения
     */
    @Autowired
    private PropertiesService propertiesService;

    /**
     * Сервис для чтения метаданных конфигурации
     */
    @Autowired
    private ConfReader confReader;

    /**
     * Параметр "Сценарий сборки защищенного решения"
     */
    private static final String PARAM_SCRIPT = "script";

    /**
     * Параметр "Проект"
     */
    private static final String PARAM_PROJECT = "project";

    /**
     * Параметр "Рабочий каталог"
     */
    private static final String PARAM_WORK_DIR = "workDir";

    /**
     * Параметр "Расположение XML-файлов конфигурации"
     */
    private static final String PARAM_XML_DIR = "xmlDir";

    /**
     * Параметр "Полное имя файла обработки"
     */
    private static final String PARAM_EPF_FILE = "epfFile";

    /**
     * Параметр "Файл защищенной обработки"
     */
    private static final String PARAM_DATAFILE_FILE = "datafileFile";

    /**
     * Параметр "Метаданные конфигурации"
     */
    private static final String PARAM_CONF = "conf";

    /**
     * Параметр "Информационная база для построения"
     */
    private static final String PARAM_INFO_BASE = "infoBase";

    /**
     * Параметры построения в рамках сессии
     */
    private final Map<UUID, HashMap<String, Object>> sessions = new HashMap<>();

    /**
     * Запуск построения защищенного решения
     * @param script Сценарий сборки
     * @param infoBase Информационная база для сборки
     * @return Расположение "скомпилированного" файла
     * @throws LicenceException Исключения при работе с утилитой лицензирования СЛК
     * @throws IOException Исключения при работе с файлами
     */
    public Path build(LicenceBuildScript script, InfoBase infoBase) throws LicenceException, IOException {
        UUID session = UUID.randomUUID();
        HashMap<String, Object> sessionParam = new HashMap<>();
        sessions.put(session, sessionParam);

        setParam(session, PARAM_SCRIPT, script);
        setParam(session, PARAM_INFO_BASE, infoBase);

        Path workDir = null;
        try {
            sessionParam.put(PARAM_PROJECT, script.getProject());

            workDir = Files.createTempDirectory(session.toString());
            sessionParam.put(PARAM_WORK_DIR, workDir);

            Path xmlDir = workDir.resolve("xml");
            Files.createDirectory(xmlDir);
            setParam(session, PARAM_XML_DIR, xmlDir);

            copySourceCode(session);
            readConf(session);
            createEpf(session);
            createDatafile(session);
            insertProtectedModule(session);
            updateSourceCode(session);
            removeDataProcessor(session);

            return makeCfCfe(session);

        } catch (IOException | YellowException | ReaderException ex) {
            throw new LicenceException(ex.getMessage());

        } finally {
            sessions.remove(session);
            if (workDir != null && Files.exists(workDir)) {
                FileSystemUtils.deleteRecursively(workDir);
            }
        }
    }

    /**
     * Получение параметра построения в рамках сессии
     * @param session Уникальный идентификатор (UUID)
     * @param name Имя параметра
     * @return Значение параметра
     */
    private Object getParam(UUID session, String name) {
        return sessions.get(session).get(name);
    }

    /**
     * Установка параметра построения в рамках сессии
     * @param session Уникальный идентификатор (UUID)
     * @param name Имя параметра
     * @param value Значение параметра
     */
    private void setParam(UUID session, String name, Object value) {
        sessions.get(session).put(name, value);
    }

    /**
     * Копирование исходных файлов проекта (XML-файлы)
     * @param session Уникальный идентификатор сессии
     * @throws LicenceException Исключение может быть выброшено в случае, если проект не найден
     * @throws IOException Возможна ошибка при рекурсивном копировании каталога с XML-файлами
     */
    private void copySourceCode(UUID session) throws LicenceException, IOException {
        Project project = (Project) getParam(session, PARAM_PROJECT);
        if (project == null) {
            throw new LicenceException("Не найден проект");
        }

        Path source = projectsService.getTemporaryLocation(project);
        if (!Files.exists(source)) {
            throw new LicenceException("Не найден каталог проекта, возможно необходимо обновить исходники проекта");
        }

        Path target = ((Path) getParam(session, PARAM_XML_DIR));
        FileSystemUtils.copyRecursively(source, target);
    }

    /**
     * Чтение конфигурации на основании XML-файлов
     * @param session Уникальный идентификатор сессии
     * @throws ReaderException В случае ошибок парсинга файлов конфигурации
     */
    private void readConf(UUID session) throws ReaderException {
        setParam(session, PARAM_CONF, confReader.read((Path) getParam(session, PARAM_XML_DIR)));
    }

    /**
     * Создание файла внешней обработки (epf) на основе исходника в XML
     *
     * @param session Уникальный идентификатор сессии
     * @throws LicenceException Может быть выброшено, если не найден исполняемый файл 1С:Предприятие
     * @throws YellowException Ошибки пакетного режима 1С:Предприятие
     * @throws ReaderException Ошибки парсинга конфигураии (чтение XML-файлов)
     * @throws IOException Ошибки обработки XML-файла обработки
     */
    private void createEpf(UUID session) throws LicenceException, YellowException, ReaderException, IOException {
        LicenceBuildScript script = (LicenceBuildScript) getParam(session, PARAM_SCRIPT);

        UUID dataProcessorUuid = script.getDataProcessor();
        MetadataObject dataProcessor = confReader.findByUUID((Conf) getParam(session, PARAM_CONF), dataProcessorUuid);

        Path epfFile = ((Path) getParam(session, PARAM_WORK_DIR))
                .resolve(dataProcessor.getShortName(dataProcessor.getFile()) + ".epf");

        setParam(session, PARAM_EPF_FILE, epfFile);

        Files.write(
            dataProcessor.getFile(),
            Files.readString(dataProcessor.getFile())
                .replace("DataProcessor", "ExternalDataProcessor")
                .getBytes(StandardCharsets.UTF_8));

        Project project = (Project) getParam(session, PARAM_PROJECT);
        Path thickClient = Paths.get(project.getDesigner().getPath());
        if (!Files.exists(thickClient)) {
            throw new LicenceException("Исполняемый файл 1С:Предприятие не найден");
        }

        batchModeYellow.loadExternalDataProcessorOrReportFromFiles(
                new Yellow(thickClient), dataProcessor.getFile(), epfFile);
    }

    /**
     * Создание datafile-файл (файл защищенной обработки)
     * @param session Уникальный идентификатор сессии
     * @throws LicenceException Будет выброшено в случае ошибок создания защищенной обработки
     * @throws IOException Возможно на операциях удаления файлов
     */
    private void createDatafile(UUID session) throws LicenceException, IOException {
        Path workDir = (Path) getParam(session, PARAM_WORK_DIR);

        Path protectedModule = workDir.resolve("licence.datafile");
        Path dataProcessing = (Path) getParam(session, PARAM_EPF_FILE);

        LicenceBuildScript script = (LicenceBuildScript) getParam(session, PARAM_SCRIPT);
        if (script.getLicenceKey() == null) {
            throw new LicenceException("Не выбран ключ подписи");
        }
        Path cryptKey = workDir.resolve(script.getLicenceKey().getName());
        Files.write(cryptKey, script.getLicenceKey().getAttachedFile());

        if (propertiesService.getLicenceAppFile() == null) {
            throw new LicenceException("Не установлено значение параметра <LICENCE_APP_FILE>");

        } else if (!Files.exists(propertiesService.getLicenceAppFile())) {
            throw new LicenceException("Не найдена утилита лицензирования СЛК");
        }

        batchModeLicence.createLicenceObjectsFile(
                propertiesService.getLicenceAppFile(),
                protectedModule,
                dataProcessing,
                cryptKey,
                script.getLicenceKey().getName()
        );

        setParam(session, PARAM_DATAFILE_FILE, protectedModule);

        Files.delete(dataProcessing);
        Files.delete(cryptKey);
    }

    /**
     * Вставляет в конфигурацию защищенный модуль (защишенную обработку)
     * @param session Уникальный идентификатор сессии
     * @throws ReaderException Может быть выброшено, если не найден общий макет или в случае ошибок парсинга файлов
     * @throws IOException Операции удаления временных файлов, созданных для "подписи" защищенной обработки
     */
    private void insertProtectedModule(UUID session) throws ReaderException, IOException {
        LicenceBuildScript script = (LicenceBuildScript) getParam(session, PARAM_SCRIPT);

        UUID templateUuid = script.getTemplate();
        MetadataObject template = confReader.findByUUID((Conf) getParam(session, PARAM_CONF), templateUuid);

        Path templateBin = template.getFile()
            .getParent()
            .resolve(template.getShortName(template.getFile()))
            .resolve("Ext").resolve("Template.bin");
        Files.deleteIfExists(templateBin);

        Path protectedModule = (Path) getParam(session, PARAM_DATAFILE_FILE);
        Files.copy(protectedModule, templateBin);
        Files.delete(protectedModule);
    }

    /**
     * Удаление обработки из конфигурации
     * @param session Уникальный идентификатор сессии
     * @throws ReaderException Может быть выброшено, если не найдена обработка или в случае ошибок парсинга файлов
     * @throws IOException Операция удаления файлов обработки
     */
    private void removeDataProcessor(UUID session) throws ReaderException, IOException {
        LicenceBuildScript script = (LicenceBuildScript) getParam(session, PARAM_SCRIPT);

        UUID dataProcessorUuid = script.getDataProcessor();
        MetadataObject dataProcessor = confReader.findByUUID((Conf) getParam(session, PARAM_CONF), dataProcessorUuid);

        Path confXml = ((Path) getParam(session, PARAM_XML_DIR)).resolve("Configuration.xml");

        String shortName = dataProcessor.getShortName(dataProcessor.getFile());

        Files.write(confXml,
            Files.readString(confXml)
                .replace("<DataProcessor>" + shortName + "</DataProcessor>", "")
                .getBytes(StandardCharsets.UTF_8));

        FileSystemUtils.deleteRecursively(
            dataProcessor.getFile()
                .getParent()
                .resolve(shortName));
        Files.delete(dataProcessor.getFile());
    }

    /**
     * Обновление общего модуля, в котором реализован вызов защищенного модуля (защищенной обработки)
     * @param session Уникальный идентификатор сессии
     * @throws ReaderException Может быть выброшено, если не найден общий модуль или в случае ошибок парсинга файлов
     * @throws IOException При удалении или записи BSL-файла
     */
    private void updateSourceCode(UUID session) throws ReaderException, IOException {
        LicenceBuildScript script = (LicenceBuildScript) getParam(session, PARAM_SCRIPT);

        UUID commonModuleUuid = script.getCommonModule();
        MetadataObject commonModule = confReader.findByUUID((Conf) getParam(session, PARAM_CONF), commonModuleUuid);

        Path moduleBsl = commonModule.getFile()
                .getParent()
                .resolve(commonModule.getShortName(commonModule.getFile()))
                .resolve("Ext").resolve("Module.bsl");
        Files.deleteIfExists(moduleBsl);

        Files.write(moduleBsl, script.getTargetText().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Формирование cf/cfe файла с защищенным модулем (обработкой)
     * @param session Уникальный идентификатор сессии
     * @return Расположение временного файла
     * @throws IOException Возможно при создании временного файла
     * @throws YellowException При операциях загрузки конфигурации из XML-файлов или формировании cf/cfe-файла
     */
    private Path makeCfCfe(UUID session) throws IOException, YellowException {
        InfoBase infoBase = (InfoBase) getParam(session, PARAM_INFO_BASE);

        ru.pf.yellow.InfoBase yellowInfoBase = new ru.pf.yellow.InfoBase(
                Paths.get(infoBase.getPath()), infoBase.getLogin(), infoBase.getPassword());

        Project project = (Project) getParam(session, PARAM_PROJECT);
        Yellow yellow = new Yellow(Paths.get(project.getDesigner().getPath()));

        Path xmlDir = (Path) getParam(session, PARAM_XML_DIR);

        boolean isExtension = project.getType() == Project.Type.EXTENSION;

        Path makeFile = Files.createTempFile(session.toString(), isExtension ? ".cfe" : ".cf");
        if (isExtension) {
            Extension extension = new Extension(project.getNameExtension());
            batchModeYellow.loadConfigFromFiles(yellow, yellowInfoBase, extension, xmlDir);
            batchModeYellow.dumpCfg(yellow, yellowInfoBase, extension, makeFile);
        } else {
            batchModeYellow.loadConfigFromFiles(yellow, yellowInfoBase, xmlDir);
            batchModeYellow.dumpCfg(yellow, yellowInfoBase, makeFile);
        }

        return makeFile;
    }
}
