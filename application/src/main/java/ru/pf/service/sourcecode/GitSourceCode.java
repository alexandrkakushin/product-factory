package ru.pf.service.sourcecode;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pf.entity.Project;
import ru.pf.service.ProjectsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Компонент для работы с GIT-репозиторием
 * @author a.kakushin
 */
@Component
@SourceCodeRepository(SourceCodeRepository.Types.GIT)
public class GitSourceCode implements SourceCode {

    @Autowired
    ProjectsService projectsService;

    @Override
    public void pull(Project project) throws SourceCodeException {
        Path target = projectsService.getTemporaryLocation(project);

        try {
            if (!Files.exists(target)) {
                Files.createDirectories(target);
            }

            if (Files.exists(target.resolve(".git"))) {
                try (org.eclipse.jgit.api.Git repository = org.eclipse.jgit.api.Git.open(target.toFile())) {
                    repository.pull().call();
                }
            } else {
                org.eclipse.jgit.api.Git.cloneRepository()
                        .setURI(project.getGit().getFetchUrl())
                        .setDirectory(target.toFile())
                        .setBranch(project.getGit().getDefaultBranch())
                        .call();
            }
        } catch (IOException | GitAPIException ex) {
            throw new SourceCodeException(ex.getMessage());
        }
    }
}
