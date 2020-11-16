package ru.pf.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author a.kakushin
 */
@Service
public class ThickClientService {

    @Autowired
    private PropertiesService propertiesService;

    public List<Path> versions() throws IOException {
        List<Path> versions = new ArrayList<>();
        List<Path> directories = existingDirectories();

        if (isWindows()) {            
            for (final Path dir : directories) {
                Files.list(dir)
                    .filter(path -> Files.isDirectory(path))
                    .filter(path -> path.getFileName().toString().startsWith("8."))
                    .forEach(versions::add);
            }        
        } else {
            versions = directories;
        }

        return versions;
    }

    private List<Path> existingDirectories() {
        final List<Path> directories = new ArrayList<>();
        final List<Path> existsing = new ArrayList<>();

        if (propertiesService.getDirVersions1C() != null) {
            if (!propertiesService.getDirVersions1C().toString().isEmpty()) {
                directories.add(propertiesService.getDirVersions1C());
            }
        }

        if (isWindows()) {
            directories.add(Paths.get("C:\\Program Files\\1cv8"));
            directories.add(Paths.get("C:\\Program Files (x86)\\1cv8"));            
        } else {
            directories.add(Paths.get("/opt/1C/v8.3/i386/1cv8"));
            directories.add(Paths.get("/opt/1C/v8.3/x86_64/1cv8"));
        }

        for (final Path dir : directories) {
            if (Files.exists(dir)) {
                existsing.add(dir);
            }
        }

        return existsing;
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }
}