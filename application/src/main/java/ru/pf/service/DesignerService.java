package ru.pf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.pf.entity.Designer;
import ru.pf.entity.InfoBase;
import ru.pf.service.exception.CrServiceException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DesignerService {

    /**
     * Логгер сервиса по работе с конфигураторами
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Получение имен расширений
     * @param designer Конфигуратор
     * @param infoBase Информационная база
     * @return Список имен
     */
    List<String> getExtensions(Designer designer, InfoBase infoBase) {
        List<String> result = new ArrayList<>();

            // "C:\Program Files\1cv8\8.3.18.1208\bin\1cv8.exe" DESIGNER /F "G:\3d_temp\Temp" /N "Федоров (администратор)" /P "" /DumpDBCfgList -AllExtensions /Out "G:\3d_temp\logs.txt"

            String sb = "DESIGNER  /F" +
                    "\"" + infoBase.toString() + "\"" +
                    "/N" +
                    "/P" +
                    " /DumpDBCfgList -AllExtension";

            // return (startProcess(sb) == 0);

        return result;
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
    private int startProcess(Designer designer, String parameters) {
        int exitCode = -1;

        try {
            Path version1c = Paths.get(designer.getPath());
            if (!Files.exists(version1c)) {
                throw new FileNotFoundException("Platform not found: " + designer.getPath());
            }

            Path app1c = isWindows() ? version1c.resolve("bin").resolve("1cv8.ext") : version1c.resolve("1cv8");

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(
                    isWindows() ? "cmd.exe" : "bash",
                    isWindows() ? "/c" : "-c",
                    app1c + " " + parameters);

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
}
