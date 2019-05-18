package ru.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.reader.ConfReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author a.kakushin
 */
@Service
public class ProjectsService {

    @Autowired
    PropertiesService propertiesService;

    @Autowired
    ConfReader confReader;

    private Conf getConfFromGit(Project project) throws IOException {
        Path storage = propertiesService.getStorage();
        Path target = storage
                .resolve(project.getId().toString())
                .resolve("git");

        return confReader.read(target);
    }

    private Conf getConfFromCr(Project project) throws IOException {
        // todo: http-взаимодействие с сервером хранилища конфигураций
        return null;
    }

    private Conf getConfFromDirectory(Project project) throws IOException {
        Path directory = Paths.get(project.getDirectory());
        if (Files.exists(directory)) {
            return confReader.read(directory);
        } else {
            throw new IOException("Каталог XML-файлов конфигурации не найден");
        }
    }

    public Conf getConf(Project project) throws IOException {
        if (project.getSourceType() == null) {
            throw new IOException("Тип источника исходных кодов не задан");
        }

        if (project.getSourceType() == Project.SourceType.GIT) {
            return getConfFromGit(project);

        } else if (project.getSourceType() == Project.SourceType.CR) {
            return getConfFromCr(project);

        } else if (project.getSourceType() == Project.SourceType.DIRECTORY) {
            return getConfFromDirectory(project);

        } else {
            // todo: добавить свои исключения
            throw new IOException("Неизвестный тип источника исходных кодов");
        }
    }
}
