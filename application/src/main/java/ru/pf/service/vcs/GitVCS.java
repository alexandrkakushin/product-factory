package ru.pf.service.vcs;

import org.eclipse.jgit.api.Git;
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
public class GitVCS implements VCS {

    @Autowired
    ProjectsService projectsService;

    @Override
    public void pull(Project project) throws VCSException {
        Path target = projectsService.getTemporaryLocation(project);

        try {
            if (!Files.exists(target)) {
                Files.createDirectories(target);
            }

            if (Files.exists(target.resolve(".git"))) {
                try (Git repository = Git.open(target.toFile())) {
                    repository.pull().call();
                }
            } else {
                Git.cloneRepository()
                        .setURI(project.getGit().getFetchUrl())
                        .setDirectory(target.toFile())
                        .setBranch(project.getGit().getDefaultBranch())
                        .call();
            }
        } catch (IOException | GitAPIException ex) {
            throw new VCSException(ex.getMessage());
        }
    }
}
