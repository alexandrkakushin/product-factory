package ru.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import ru.pf.core.sourcecode.SourceCodeException;
import ru.pf.core.sourcecode.SourceCodeRepository;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.reader.ConfReader;
import ru.pf.metadata.reader.ReaderException;
import ru.pf.sourcecode.UpdateConfService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
     * Утилитный класс-читатеть конфигурации
     */
    @Autowired
    private ConfReader confReader;

    @Autowired
    private UpdateConfService updateConfService;

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
            FileSystemUtils.deleteRecursively(temp);
        }
        Files.createDirectories(temp);

        if (typeRepository == null) {
            throw new SourceCodeException("Тип источника исходных кодов не задан");
        }

        updateConfService.update(project);
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
        return propertiesService.getStorageProject(project);
    }
}