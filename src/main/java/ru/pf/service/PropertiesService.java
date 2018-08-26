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

    public String get(String name) {
        return propertiesRepository.getByName(name)
            .map(item -> item.getValue())
                .orElse("");
    }

    public Path getStorage() {
        return Paths.get(this.get(Properties.STORAGE));
    }
}
