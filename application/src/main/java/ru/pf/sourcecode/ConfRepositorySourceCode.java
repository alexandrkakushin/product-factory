package ru.pf.sourcecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pf.core.sourcecode.SourceCodeException;
import ru.pf.core.sourcecode.SourceCodeRepository;
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
        if (project.getType() == Project.Type.CONF) {
            pullConf(project);
        } else {
            pullExtension(project);
        }
    }

    /**
     * Получение исходных кодов конфигурации из хранилища
     * Для этого создается база "пустышка", в которую загружается конфигурация из хранилища, а затем выгружается в файлы
     * @param project Проект-конфигурация
     * @throws SourceCodeException Может быть выброшено в случае ошибок получения исходных кодов
     */
    protected void pullConf(Project project) throws SourceCodeException {
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

    /**
     * Получение исходных кодов расширения информационной базы из хранилища конфигурации
     * @param project Проект-расширение
     * @throws SourceCodeException Может быть выброшено в случае ошибок получения исходных кодов
     */
    protected void pullExtension(Project project) throws SourceCodeException {
        ru.pf.entity.InfoBase infoBaseDb = project.getInfoBase();
        if (infoBaseDb == null) {
            throw new SourceCodeException("Не заполнена информационная база");
        }

        try {
            Path pathXml = projectsService.getTemporaryLocation(project);

            Path pathIb = Paths.get(infoBaseDb.getPath());
            if (!Files.exists(pathIb)) {
                throw new SourceCodeException(String.format("Каталог информационной базы < %s > не найден", pathIb));
            }

            Yellow yellow = new Yellow(Paths.get(project.getDesigner().getPath()));
            InfoBase infoBase = new InfoBase(pathIb, infoBaseDb.getLogin(), infoBaseDb.getPassword());

            Cr crDb = project.getCr();
            Extension extension = new Extension(project.getNameExtension());
            batchModeYellow.configurationRepositoryUpdateCfg(yellow,
                    infoBase,
                    new ru.pf.yellow.ConfigurationRepository(crDb.getAddress(), crDb.getLogin(), crDb.getPassword()),
                    extension);

            batchModeYellow.dumpConfigToFiles(yellow, infoBase, pathXml, extension);

        } catch (YellowException ex) {
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

        if (project.getType() == Project.Type.EXTENSION) {
            validateExtension(project);
        }
    }

    /**
     * Проверка расширения информационной базы
     * @param project Проект
     * @throws SourceCodeException Будет выброшено, если не заполнена информационная база
     */
    private void validateExtension(Project project) throws SourceCodeException {
        if (project.getInfoBase() == null) {
            throw new SourceCodeException("Для обновления из хранилища конфигурации необходимо выбрать информационную базу");
        }

        if (project.getNameExtension() == null) {
            throw new SourceCodeException("Не заполнено имя расширения");
        }
    }
}
