package ru.pf.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.entity.Project;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.reader.ConfReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

/**
 * @author a.kakushin
 */
@Service
public class ProjectsService {

    // todo: перенести получение временных директорий с PropertiesService, чтобы исключить зависимость

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private GitService gitService;

    @Autowired
    private ConfReader confReader;

    @Autowired
    private CrService crService;

    private boolean updateFromDirectory(Project project) throws IOException {
        Path temp = getTemporaryLocation(project);
        Path src = Paths.get(project.getDirectory());
        if (!Files.exists(src)) {
            throw new FileNotFoundException("Директория не найдена");
        }

        // todo: исправить, исключение не ловится
        Files.walk(src)
                .forEach(source -> {
                    try {
                        Files.copy(source, temp.resolve(src.relativize(source)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return true;
    }

    private boolean updateFromCr(Project project) {
        try {
            return crService.fetch(project);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateFromGit(Project project) throws IOException {
        try {
            gitService.pull(project);
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Project project) throws IOException {

        Path temp = getTemporaryLocation(project);
        if (project.getSourceType() == Project.SourceType.DIRECTORY && Files.exists(temp)) {
            Files.walk(temp)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        Files.createDirectories(temp);

        if (project.getSourceType() == null) {
            throw new IOException("Тип источника исходных кодов не задан");
        }

        if (project.getSourceType() == Project.SourceType.GIT) {
            return updateFromGit(project);

        } else if (project.getSourceType() == Project.SourceType.CR) {
            return updateFromCr(project);

        } else if (project.getSourceType() == Project.SourceType.DIRECTORY) {
            return updateFromDirectory(project);

        } else {
            // todo: добавить свои исключения
            throw new IOException("Неизвестный тип источника исходных кодов");
        }
    }

    public Conf getConf(Project project) throws IOException {
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
