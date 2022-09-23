package ru.pf.sourcecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.core.sourcecode.SourceCodeException;
import ru.pf.core.sourcecode.SourceCodeRepository;
import ru.pf.entity.Project;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.EnumMap;

@Service
public class UpdateConfService {

    /**
     * Сервис для работы с GIT-репозиториями
     */
    @Autowired
    @SourceCodeRepository(SourceCodeRepository.Types.GIT)
    private SourceCode gitSourceCode;

    /**
     * Сервис для работы с хранилищами конфигураций
     */
    @Autowired
    @SourceCodeRepository(SourceCodeRepository.Types.CONFIGURATION_REPOSITORY)
    private SourceCode configurationRepositorySourceCode;

    /**
     * Компонент для копирования исходных кодов из директории
     */
    @Autowired
    @SourceCodeRepository(SourceCodeRepository.Types.DIRECTORY)
    private SourceCode directorySourceCode;

    /**
     * Сервис для обновления исходников из информационной базы
     */
    @Autowired
    @SourceCodeRepository(SourceCodeRepository.Types.INFO_BASE)
    private SourceCode infoBaseSourceCode;

    /**
     * Связь типов источников кода и бинов-"репозиториев"
     */
    private EnumMap<SourceCodeRepository.Types, SourceCode> vcsMap;

    @PostConstruct
    private void createVcsMap() {
        vcsMap = new EnumMap<>(SourceCodeRepository.Types.class);
        vcsMap.put(SourceCodeRepository.Types.GIT, gitSourceCode);
        vcsMap.put(SourceCodeRepository.Types.CONFIGURATION_REPOSITORY, configurationRepositorySourceCode);
        vcsMap.put(SourceCodeRepository.Types.DIRECTORY, directorySourceCode);
        vcsMap.put(SourceCodeRepository.Types.INFO_BASE, infoBaseSourceCode);
    }

    /**
     * Обновление исходных кодов проекта
     * @param project Проект
     * @throws IOException Исключение при работе с файловыми операциями, в частности операций с каталогом хранения исходных кодов
     * @throws SourceCodeException Исключение при работе с VCS
     */
    public void update(Project project) throws IOException, SourceCodeException {
        SourceCodeRepository.Types typeRepository = project.getTypeOfSourceCodeRepository();
        SourceCode sourceCodeBean = vcsMap.get(typeRepository);
        if (sourceCodeBean != null) {
            sourceCodeBean.pull(project);
        } else {
            throw new SourceCodeException("Неизвестный тип источника исходных кодов");
        }
    }
}
