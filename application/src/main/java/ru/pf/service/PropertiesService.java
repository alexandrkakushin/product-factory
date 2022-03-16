package ru.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.entity.Property;
import ru.pf.repository.PropertiesRepository;
import ru.pf.utility.Properties;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Сервис для работы с настройками приложения
 * @author a.kakushin
 */
@Service
public class PropertiesService {

    @Autowired
    PropertiesRepository propertiesRepository;

    public String get(final String name) {
        return propertiesRepository.getByName(name)
            .map(Property::getValue)
                .orElse(null);
    }

    public Path getStorage() {
        String path = this.get(Properties.STORAGE);
        if (path == null || path.isEmpty()) {
            path = System.getProperty("java.io.tmpdir");
        }
        return Paths.get(path);
    }

    public int getCheckNameLength() {
        final String saved = this.get(Properties.CHECK_NAME_LENGTH);
        return saved != null ? Integer.parseInt(saved) : 0;
    }

    public int getCheckLineSize() {
        final String saved = this.get(Properties.CHECK_LINE_SIZE);
        return saved != null ? Integer.parseInt(saved) : 0;
    }

    public Path getDirVersions1C() {
        String saved = this.get(Properties.DIR_VERSIONS_1C);
        return saved != null ? Paths.get(saved) : null;
    }

    /**
     * Получение расположения файла утилиты лицензирования СЛК
     * @return Расположение файла
     */
    public Path getLicenceAppFile() {
        String saved = this.get(Properties.LICENCE_APP_FILE);
        return saved != null ? Paths.get(saved) : null;
    }
}
