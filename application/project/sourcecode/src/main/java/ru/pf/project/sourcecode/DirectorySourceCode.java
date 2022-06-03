package ru.pf.project.sourcecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import ru.pf.entity.Project;
import ru.pf.service.ProjectsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Компонент копирования исходных кодов из директории
 * @author a.kakushin
 */
@Component
@SourceCodeRepository(SourceCodeRepository.Types.DIRECTORY)
public class DirectorySourceCode implements SourceCode {

    @Autowired
    ProjectsService projectsService;

    @Override
    public void pull(Project project) throws SourceCodeException {
        Path target = projectsService.getTemporaryLocation(project);
        Path src = Paths.get(project.getDirectory());
        if (!Files.exists(src)) {
            throw new SourceCodeException("Директория размещения исходных кодов не найдена");
        }

        try {
            FileSystemUtils.copyRecursively(src, target);
        } catch (IOException ex) {
            throw new SourceCodeException(ex.getMessage());
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
