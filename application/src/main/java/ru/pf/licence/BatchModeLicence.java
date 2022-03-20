package ru.pf.licence;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Пакетный режим для управления утилитой лицензирования СЛК
 * @author a.kakushin
 */
@Component
public class BatchModeLicence {

    public void createLicenceObjectsFile(Path executable, Path output, Path dataProcessing, Path cryptKey, String keyId) throws LicenceException {
        Command command =
                new Command().createLicenceObjectsFile(output)
                        .source(dataProcessing)
                        .cryptkey(cryptKey)
                        .serie(keyId)
                        .yes()
                        .build();

        startProcess(executable, command);
    }

    /**
     * Класс для формирования аргументов запуска
     */
    public static class Command {
        private List<String> args = new ArrayList<>();

        public CommandBuilder createLicenceObjectsFile(Path fileName) {
            getArgs().add("c " + "\"" + fileName + "\"");
            return new CommandBuilder(this);
        }

        private List<String> getArgs() {
            return args;
        }

        public String get() {
            return
                    this.getArgs().stream()
                            .collect(Collectors.joining(" "));
        }

        /**
         * "Построитель команды", реализация основных команд конфигуратора
         */
        public static class CommandBuilder {
            private final Command command;

            public CommandBuilder(Command command) {
                this.command = command;
            }

            /**
             * Серия ключа
             * @param keyId Серия ключа
             * @return Построитель команды
             */
            public CommandBuilder serie(String keyId) {
                this.command.getArgs().add("--serie=" + keyId);
                return this;
            }

            /**
             * Серия - имя файла
             * @param fileName Имя файла
             * @return Построитель команды
             */
            public CommandBuilder cryptkey(Path fileName) {
                this.command.getArgs().add("--cryptkey=" + "\"" + fileName + "\"");
                return this;
            }

            /**
             * Всегда "отвечать" ДА на все вопросы
             * @return Построитель команды
             */
            public CommandBuilder yes() {
                this.command.getArgs().add("--yes");
                return this;
            }

            /**
             * Кодировка исходного файла, возможные варианты {{ansi|utf8|utf16}}, по умолчанию "ansi"
             * @param value Кодировка
             * @return Построитель команды
             */
            public CommandBuilder encoding(String value) {
                this.command.getArgs().add("--encoding=" + value);
                return this;
            }

            /**
             * Имя выходного файла (сгенерированного защищенного модуля)
             * @param fileName Имя файла
             * @return Построитель команды
             */
            public CommandBuilder source(Path fileName) {
                this.command.getArgs().add("\"" + fileName + "\"");
                return this;
            }

            public Command build() {
                return this.command;
            }
        }
    }

    /**
     * Определяет, запущено ли в приложение в ОС в Windows
     *
     * @return Булево
     */
    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    /**
     * Запуск утилиты лицензирования (licenceedit)
     *
     * @param executable Файл утилиты лицензирования
     * @param command Аргументы запуска
     * @throws LicenceException Исключение при работе с утилитой лицензирования
     */
    private void startProcess(Path executable, Command command) throws LicenceException {
        startProcess(executable, command.get());
    }

    /**
     * Запуск утилиты лицензирования (licenceedit)
     * @param executable Файл утилиты лицензирования
     * @param parameters Параметры запуска
     * @throws LicenceException Исключение при работе с утилитой лицензирования
     */
    private void startProcess(Path executable, String parameters) throws LicenceException {
        int exitCode = -1;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(
                    isWindows() ? "cmd.exe" : "bash",
                    isWindows() ? "/c" : "-c",
                    executable + " " + parameters);

            Process process = processBuilder.start();
            exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new LicenceException("Exited with error code: " + exitCode);
            }

        } catch (InterruptedException | IOException e) {
            throw new LicenceException(e);
        }
    }
}
