package ru.pf.service.vcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pf.entity.Cr;
import ru.pf.entity.Project;
import ru.pf.service.ProjectsService;
import ru.pf.yellow.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Компонент для работы с хранилищем конфигурации
 *
 * @author a.kakushin
 */
@Component
@SourceCodeRepository(SourceCodeRepository.Types.CONFIGURATION_REPOSITORY)
public class ConfigurationRepositoryVCS implements VCS {

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private BatchMode yellowBatchMode;

    /**
     * Обновление из хранилища конфигурации
     *
     * @param project Проект
     * @throws VCSException Ошибки при создании информационной базы, обновлении из хранилища конфигурации
     */
    @Override
    public void pull(Project project) throws VCSException {

        validate(project);

        Path pathXml = projectsService.getTemporaryLocation(project);
        Path pathIb = pathXml.resolve(".cr");

        try {
            Yellow yellow = new Yellow(Paths.get(project.getDesigner().getPath()));

            if (!Files.exists(pathIb)) {
                Files.createDirectory(pathIb);
                yellowBatchMode.createFileInfoBase(yellow, pathIb);
            }

            InfoBase infoBase = new InfoBase(pathIb);

            Cr crDb = project.getCr();

            yellowBatchMode.configurationRepositoryUpdateCfg(yellow,
                    infoBase,
                    new ConfigurationRepository(crDb.getAddress(), crDb.getLogin(), crDb.getPassword()));

            yellowBatchMode.dumpConfigToFiles(yellow, infoBase, pathXml);

        } catch (YellowException | IOException ex) {
            throw new VCSException(ex);
        }
    }

    private void validate(Project project) throws VCSException {
        if (project.getDesigner() == null) {
            throw new VCSException("Не выбран конфигуратор");
        }

        if (project.getDesigner().getPath().isBlank()) {
            throw new VCSException("Не указано расположение конфигуратора");
        }

        if (project.getCr() == null) {
            throw new VCSException("Не выбрано хранилище конфигурации");
        }

        if (project.getCr().getAddress().isBlank()) {
            throw new VCSException("Не заполнен адрес хранилища конфигурации");
        }
    }
}
