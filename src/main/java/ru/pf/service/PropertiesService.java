package ru.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.repository.PropertiesRepository;
import ru.pf.utility.Properties;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author a.kakushin
 */
@Service
public class PropertiesService {

    @Autowired
    PropertiesRepository propertiesRepository;

    public String get(final String name) {
        return propertiesRepository.getByName(name)
            .map(item -> item.getValue())
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
        return saved != null ? Integer.valueOf(saved) : 0;
    }

    public int getCheckLineSize() {
        final String saved = this.get(Properties.CHECK_LINE_SIZE);
        return saved != null ? Integer.valueOf(saved) : 0;
    }

    public Path getDirVersions1C() {
        return Paths.get(this.get(Properties.DIR_VERSIONS_1C));
    }
}
