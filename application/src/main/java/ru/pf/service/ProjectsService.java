package ru.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.reader.ConfReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.service.sourcecode.SourceCodeRepository;
import ru.pf.service.sourcecode.SourceCode;
import ru.pf.service.sourcecode.SourceCodeException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.stream.Stream;

/**
 * Сервис для работы с проектами
 * @author a.kakushin
 */
@Service
public class ProjectsService {

    /**
     * Сервис для чтения параметров приложения
     */
    @Autowired
    private PropertiesService propertiesService;

    /**
     * Сервис для работы с GIT-репозиториями
     */
    @Autowired
    @SourceCodeRepository(SourceCodeRepository.Types.GIT)
    private SourceCode gitSourceCode;

    /**
     * Сервис для работы с хранилищами конфигураций
     */
    @Autowired
    @SourceCodeRepository(SourceCodeRepository.Types.CONFIGURATION_REPOSITORY)
    private SourceCode configurationRepositorySourceCode;

    /**
     * Компонент для копирования исходных кодов из директории
     */
    @Autowired
    @SourceCodeRepository(SourceCodeRepository.Types.DIRECTORY)
    private SourceCode directorySourceCode;

    /**
     * Утилитный класс-читатеть конфигурации
     */
    @Autowired
    private ConfReader confReader;

    /**
     * Связь типов источников кода и бинов-"репозиториев"
     */
    private EnumMap<SourceCodeRepository.Types, SourceCode> vcsMap;

    @PostConstruct
    private void createVcsMap() {
        vcsMap = new EnumMap<>(SourceCodeRepository.Types.class);
        vcsMap.put(SourceCodeRepository.Types.GIT, gitSourceCode);
        vcsMap.put(SourceCodeRepository.Types.CONFIGURATION_REPOSITORY, configurationRepositorySourceCode);
        vcsMap.put(SourceCodeRepository.Types.DIRECTORY, directorySourceCode);
    }

    /**
     * Обновление исходных кодов проекта
     * @param project Проект
     * @throws IOException Исключение при работе с файловыми операциями, в частности операций с каталогом хранения исходных кодов
     * @throws SourceCodeException Исключение при работе с VCS
     */
    public void update(Project project) throws IOException, SourceCodeException {

        SourceCodeRepository.Types typeRepository = project.getTypeOfSourceCodeRepository();

        Path temp = getTemporaryLocation(project);
        if (typeRepository == SourceCodeRepository.Types.DIRECTORY && Files.exists(temp)) {
            try (Stream<Path> pathStream = Files.walk(temp)) {
                pathStream
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
        Files.createDirectories(temp);

        if (typeRepository == null) {
            throw new SourceCodeException("Тип источника исходных кодов не задан");
        }

        SourceCode sourceCodeBean = vcsMap.get(typeRepository);
        if (sourceCodeBean != null) {
            sourceCodeBean.pull(project);
        } else {
            throw new SourceCodeException("Неизвестный тип источника исходных кодов");
        }
    }

    /**
     * Чтение конфигурации
     * @param project Проект
     * @return Конфигурация
     * @throws ReaderException Исключения парсинга XML-файлов
     */
    public Conf getConf(Project project) throws ReaderException {
        Path directory = getTemporaryLocation(project);
        if (Files.exists(directory.resolve("Configuration.xml"))) {
            return confReader.read(directory);
        } else {
            return new Conf(null);
        }
    }

    public Path getTemporaryLocation(Project project) {
        return propertiesService.getStorage().resolve(project.getId().toString());
    }
}