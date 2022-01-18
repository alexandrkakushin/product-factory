package ru.pf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.entity.Cr;
import ru.pf.entity.Project;
import ru.pf.service.exception.CrServiceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Сервис для работы с хранилищем конфигурации
 *
 * @author a.kakushin
 */
@Service
public class CrService {

    /**
     * Логгер сервиса по работе с хранилищем конфигурации
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectsService projectsService;

    /**
     * Обновление из хранилища конфигурации
     *
     * @param project Проект
     * @return Истина в случае успеха
     * @throws IOException Ошибки при работе с файловыми операциями
     */
    public boolean pull(Project project) throws IOException {
        Path target = projectsService.getTemporaryLocation(project);
        Path pathIb = target.resolve(".cr");

        if (!Files.exists(pathIb)) {
            Files.createDirectory(pathIb);
        }

        return createTempIB(pathIb)
                && loadCfg(pathIb, project.getCr())
                && dumpToFiles(pathIb, target);
    }

    private boolean createTempIB(Path pathIb) {
        if (Files.exists(pathIb.resolve("1Cv8.1CD"))) {
            return true;
        }

        String sb = "CREATEINFOBASE File=" +
                "\"" +
                pathIb +
                "\"";

        return (startProcess(sb) == 0);
    }

    private boolean loadCfg(Path pathIb, Cr cr) {
        if (cr.getAddress() == null) {
            return false;
        } else {
            if (cr.getAddress().isEmpty()) {
                return false;
            }
        }

        String sb = "DESIGNER  /F" +
                "\"" + pathIb.toString() + "\"" +
                " /ConfigurationRepositoryF " + cr.getAddress() +
                " /ConfigurationRepositoryN " + cr.getLogin() +
                " /ConfigurationRepositoryP " + cr.getPassword() +
                " /ConfigurationRepositoryUpdateCfg";

        return (startProcess(sb) == 0);
    }

    private boolean dumpToFiles(Path pathIb, Path pathXml) {
        String sb = "DESIGNER  /F" +
                "\"" + pathIb.toString() + "\"" +
                " /DumpConfigToFiles " + pathXml.toString();

        return (startProcess(sb) == 0);
    }

    /**
     * Работа приложения в Windows
     *
     * @return Булево
     */
    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    /**
     * Запуск конфигуратора 1С:Предприятие в пакетном режиме
     *
     * @param parameters Строка запуска приложения
     * @return Код завершения запускаемого приложения
     */
    private int startProcess(String parameters) {
        int exitCode = -1;

        try {
            String fileNameThickClient = getFileNameThickClient();

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(
                    isWindows() ? "cmd.exe" : "bash",
                    isWindows() ? "/c" : "-c",
                    fileNameThickClient + " " + parameters);

            Process process = processBuilder.start();
            exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new CrServiceException("Exited with error code (THICK CLIENT): " + exitCode);
            }

        } catch (InterruptedException | IOException | CrServiceException e) {
            logger.warn(String.format("Parameters: %s%nMessage: %s", parameters, e.getMessage()));
        }

        return exitCode;
    }

    /**
     * Расложение конфигуратора 1С:Предприятие
     *
     * @return Расположение в файловой системе
     * @throws CrServiceException Вызывается в случае отсутствия конфигуратора в файловой системе
     */
    private String getFileNameThickClient() throws CrServiceException {
        Path application = null;
        if (isWindows()) {
            // 
        } else {
            // В настоящее время в Linux возможно установить платформу только одной версии
            application = Paths.get("/opt/1C/v8.3/i386/1cv8");
            if (!Files.exists(application)) {
                application = Paths.get("/opt/1C/v8.3/x86_64/1cv8");
            }
        }

        if (!Files.exists(application)) {
            throw new CrServiceException("Платформа 1С:Предприятие не найдена");
        }

        return application == null ? null : application.toString();
    }
}
