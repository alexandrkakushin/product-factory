package ru.pf.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.entity.Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
@Service
public class GitService {

    @Autowired
    PropertiesService propertiesService;

    public void fetch(Project project) throws GitAPIException, IOException {
        Path storage = propertiesService.getStorage();
        Path target = storage
                .resolve(project.getId().toString())
                .resolve("git");

        if (!Files.exists(target)) {
            Files.createDirectories(target);
        }

        if (Files.exists(target.resolve(".git"))) {
            Git.open(target.toFile()).fetch();
        } else {
            Git.cloneRepository()
                    .setURI(project.getGit().getFetchUrl())
                    .setDirectory(target.toFile())
                    .setBranch(project.getGit().getDefaultBranch())
                    .call();
        }
    }
}