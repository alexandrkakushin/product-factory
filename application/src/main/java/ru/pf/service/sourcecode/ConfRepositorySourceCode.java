package ru.pf.service.sourcecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pf.entity.Cr;
import ru.pf.entity.Project;
import ru.pf.service.ProjectsService;
import ru.pf.yellow.InfoBase;
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
public class ConfRepositorySourceCode implements SourceCode {

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private BatchModeYellow batchModeYellow;

    /**
     * Обновление из хранилища конфигурации
     *
     * @param project Проект
     * @throws SourceCodeException Ошибки при создании информационной базы, обновлении из хранилища конфигурации
     */
    @Override
    public void pull(Project project) throws SourceCodeException {

        validate(project);

        Path pathXml = projectsService.getTemporaryLocation(project);
        Path pathIb = pathXml.resolve(".cr");

        try {
            Yellow yellow = new Yellow(Paths.get(project.getDesigner().getPath()));

            if (!Files.exists(pathIb)) {
                Files.createDirectory(pathIb);
                batchModeYellow.createFileInfoBase(yellow, pathIb);
            }

            InfoBase infoBase = new InfoBase(pathIb);

            Cr crDb = project.getCr();

            batchModeYellow.configurationRepositoryUpdateCfg(yellow,
                    infoBase,
                    new ru.pf.yellow.ConfigurationRepository(crDb.getAddress(), crDb.getLogin(), crDb.getPassword()));

            batchModeYellow.dumpConfigToFiles(yellow, infoBase, pathXml);

        } catch (YellowException | IOException ex) {
            throw new SourceCodeException(ex);
        }
    }

    private void validate(Project project) throws SourceCodeException {
        if (project.getDesigner() == null) {
            throw new SourceCodeException("Не выбран конфигуратор");
        }

        if (project.getDesigner().getPath().isBlank()) {
            throw new SourceCodeException("Не указано расположение конфигуратора");
        }

        if (project.getCr() == null) {
            throw new SourceCodeException("Не выбрано хранилище конфигурации");
        }

        if (project.getCr().getAddress().isBlank()) {
            throw new SourceCodeException("Не заполнен адрес хранилища конфигурации");
        }
    }
}
