package ru.pf.service.vcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pf.entity.Project;
import ru.pf.service.ProjectsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Компонент копирования исходных кодов из директории
 * @author a.kakushin
 */
@Component
@SourceCodeRepository(SourceCodeRepository.Types.DIRECTORY)
public class DirectoryVCS implements VCS {

    @Autowired
    ProjectsService projectsService;

    @Override
    public void pull(Project project) throws VCSException {
        Path target = projectsService.getTemporaryLocation(project);
        Path src = Paths.get(project.getDirectory());
        if (!Files.exists(src)) {
            throw new VCSException("Директория размещения исходных кодов не найдена");
        }

        try {
            try (Stream<Path> pathStream = Files.walk(src)) {
                pathStream.forEach(source -> {
                    try {
                        Files.copy(source, target.resolve(src.relativize(source)), REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new CopyFileRuntimeException(e.getMessage());
                    }
                });
            }

        } catch (IOException ex) {
            throw new VCSException(ex.getMessage());
        }
    }

    /**
     * Класс-исключение ошибки копирования файла
     */
    static class CopyFileRuntimeException extends RuntimeException {
        public CopyFileRuntimeException(String message) {
            super(message);
        }
    }
}
