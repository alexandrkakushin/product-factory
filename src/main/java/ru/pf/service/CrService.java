package ru.pf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.entity.Cr;
import ru.pf.entity.Project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Сервис для работы с хранилищем конфигурации
 * @author a.kakushin
 */
@Service
public class CrService {

    // todo: добавить свои исключения

    @Autowired
    private ProjectsService projectsService;

    public boolean pull(Project project) throws IOException {
        Path target = projectsService.getTemporaryLocation(project);
        Path pathIb = target.resolve(".cr");

        if (!Files.exists(pathIb)) {
            Files.createDirectory(pathIb);
        }

        if (createTempIB(pathIb) && loadCfg(pathIb, project.getCr()) && dumpToFiles(pathIb, target)) {
            return true;
        }
        return false;
    }

    private boolean createTempIB(Path pathIb) {
        if (Files.exists(pathIb.resolve("1Cv8.1CD"))) {
            return true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATEINFOBASE File=")
                .append("\"")
                .append(pathIb.toString())
                .append("\"");

        return (startProcess(sb.toString()) == 0);
    }

    private boolean loadCfg(Path pathIb, Cr cr) {
        if (cr.getAddress() == null) {
            return false;
        } else {
            if (cr.getAddress().isEmpty()) {
                return false;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("DESIGNER  /F")
                .append("\"").append(pathIb.toString()).append("\"")
                .append(" /ConfigurationRepositoryF ").append(cr.getAddress())
                .append(" /ConfigurationRepositoryN ").append(cr.getLogin())
                .append(" /ConfigurationRepositoryP ").append(cr.getPassword())
                .append(" /ConfigurationRepositoryUpdateCfg");

        return (startProcess(sb.toString()) == 0);
    }

    private boolean dumpToFiles(Path pathIb, Path pathXml) {
        StringBuilder sb = new StringBuilder();
        sb.append("DESIGNER  /F")
                .append("\"").append(pathIb.toString()).append("\"")
                .append(" /DumpConfigToFiles ").append(pathXml.toString());

        return (startProcess(sb.toString()) == 0);
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    private int startProcess(String parameters) {
        int exitCode = -1;

        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command(
                    isWindows() ? "cmd.exe" : "bash",
                    isWindows() ? "/c" : "-c",
                    thickClient() + " " + parameters);

            Process process = processBuilder.start();
            exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("\nExited with error code (THICK CLIENT): " + exitCode);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return exitCode;
    }

    private String thickClient() throws FileNotFoundException {
        Path application = null;
        if (isWindows()) {
            // 
        } else {
            // Возможно установить только платформу только одной версии
            application = Paths.get("/opt/1C/v8.3/i386/1cv8");
            if (!Files.exists(application)) {
                application = Paths.get("/opt/1C/v8.3/x86_64/1cv8");
            }            
        }

        if (!Files.exists(application)) {
            // todo: добавить свои исключения
            throw new FileNotFoundException("Платформа 1С:Предприятие не найдена");
        }

        return application == null ? null : application.toString();
    }
}
