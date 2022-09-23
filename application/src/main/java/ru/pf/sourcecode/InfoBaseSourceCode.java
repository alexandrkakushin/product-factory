package ru.pf.sourcecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pf.core.sourcecode.SourceCodeException;
import ru.pf.core.sourcecode.SourceCodeRepository;
import ru.pf.entity.Project;
import ru.pf.service.ProjectsService;
import ru.pf.yellow.*;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Компонент для получения исходных кодов из информационной базы как основной конфигурации, так и расширений
 * @author a.kakushin
 */
@Component
@SourceCodeRepository(SourceCodeRepository.Types.INFO_BASE)
public class InfoBaseSourceCode implements SourceCode {

    /**
     * Сервис для работы с проектами
     */
    @Autowired
    private ProjectsService projectsService;

    /**
     * Пакетный режим 1С:Предприятие
     */
    @Autowired
    private BatchModeYellow batchModeYellow;

    /**
     * Обновление из конфигурации / конфигурации расширения информационной базы
     * @param project Проект
     * @throws SourceCodeException Ошибки при работе с информационной базой
     */
    @Override
    public void pull(Project project) throws SourceCodeException {
        validate(project);

        Path pathXml = projectsService.getTemporaryLocation(project);
        try {
            Yellow yellow = new Yellow(Paths.get(project.getDesigner().getPath()));

            InfoBase infoBase = fromDb(project.getInfoBase());

            if (project.getType() == Project.Type.EXTENSION) {
                Extension extension = new Extension(project.getNameExtension());
                batchModeYellow.dumpConfigExtensionToFiles(
                        yellow, infoBase, extension, pathXml);
            } else {
                batchModeYellow.dumpConfigToFiles(yellow, infoBase, pathXml);
            }
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

        if (project.getInfoBase() == null) {
            throw new SourceCodeException("Не выбрана информационная база");
        }

        if (project.getType() == Project.Type.EXTENSION && project.getNameExtension().isBlank()) {
            throw new SourceCodeException("Не заполнено имя расширения");
        }
    }

    /**
     * Получение объекта для работы с информационной базой
     * @param infoBaseDb Данные базы данных
     * @return Объект из модуля Yellow
     */
    private ru.pf.yellow.InfoBase fromDb(ru.pf.entity.InfoBase infoBaseDb) {
        ru.pf.yellow.InfoBase infoBase = new InfoBase();

        infoBase.setPath(Paths.get(infoBaseDb.getPath()));
        infoBase.setLogin(infoBaseDb.getLogin());
        infoBase.setPassword(infoBaseDb.getPassword());

        return infoBase;
    }
}