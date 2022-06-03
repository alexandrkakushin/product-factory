package ru.pf.project.sourcecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;

@Service
public class UpdateService {

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

}
