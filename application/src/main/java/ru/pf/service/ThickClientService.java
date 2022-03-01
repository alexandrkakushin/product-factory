package ru.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
                try (Stream<Path> pathStream = Files.list(dir)) {
                    pathStream
                        .filter(Files::isDirectory)
                        .filter(path -> path.getFileName().toString().startsWith("8."))
                        .forEach(versions::add);
                }
            }
        } else {
            versions = directories;
        }

        return versions;
    }

    private List<Path> existingDirectories() {
        final List<Path> directories = new ArrayList<>();
        final List<Path> existing = new ArrayList<>();

        Path dirVersions1C = propertiesService.getDirVersions1C();
        if (dirVersions1C != null && !dirVersions1C.toString().isEmpty()) {
            directories.add(dirVersions1C);
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
                existing.add(dir);
            }
        }

        return existing;
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }
}