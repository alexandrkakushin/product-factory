package ru.pf.yellow;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс, реализующий команды пакетного режима 1С:Предприятие
 * @author a.kakushin
 */
@Component
public class BatchModeYellow {

    /**
     * Создание файловой информационной базы
     * @param yellow "Толстый" клиент 1С:Предприятие
     * @param pathIb Каталог расположения информационной базы
     * @throws YellowException Исключение в случае ошибки создания информационной базы
     */
    public void createFileInfoBase(Yellow yellow, Path pathIb) throws YellowException {
        if (Files.exists(pathIb.resolve("1Cv8.1CD"))) {
            throw new YellowException("Информационная база уже существует");
        }
        String command = "CREATEINFOBASE File=" + "\"" + pathIb + "\"";
        startProcess(yellow, command);
    }

    /**
     * Выгрузка конфигурации информационной базы в XML-файлы
     * @param yellow "Толстый" клиент 1С:Предприятие
     * @param infoBase Информационная база
     * @param target Распложение XML-файлов
     * @throws YellowException Исключание в случае выгрузки XML-файлов
     */
    public void dumpConfigToFiles(Yellow yellow, InfoBase infoBase, Path target) throws YellowException {
        Command command =
                new Command().designer()
                        .infoBase(infoBase)
                        .dumpConfigToFiles(target)
                        .build();

        startProcess(yellow, command);
    }

    /**
     * Выгрузка конфигурации расширения в XML-файлы
     * @param yellow "Толстый" клиент 1С:Предприятие
     * @param infoBase Информационная база
     * @param extension Расширение
     * @param target Распложение XML-файлов
     * @throws YellowException Исключание в случае выгрузки XML-файлов
     */
    public void dumpConfigExtensionToFiles(Yellow yellow, InfoBase infoBase, Extension extension, Path target) throws YellowException {
        Command command =
                new Command().designer()
                        .infoBase(infoBase)
                        .dumpConfigToFiles(target)
                        .extension(extension)
                        .build();

        startProcess(yellow, command);
    }

    /**
     * Обновление конфигурации информационной базы из хранилища конфигураций
     * @param yellow "Толстый" клиент 1С:Предприятие
     * @param infoBase Информационная база
     * @param cr Хранилище конфигурации
     * @throws YellowException Исключение в случае ошибки при обновлении конфигурации из хранилища
     */
    public void configurationRepositoryUpdateCfg(Yellow yellow, InfoBase infoBase, ConfigurationRepository cr) throws YellowException {
        Command command =
                new Command().designer()
                        .infoBase(infoBase)
                        .configurationRepository(cr)
                        .configurationRepositoryUpdateCfg()
                        .disableStartupDialogs()
                        .build();

        startProcess(yellow, command);
    }

    /**
     * Получение списка расширений информационной базы
     * @param yellow Экземпляр 1С:Предприятие
     * @param infoBase Информационная база
     * @return Список расширений
     * @throws YellowException Исключение при выгрузке имен расширений
     */
    public List<Extension> dumpDBCfgList(Yellow yellow, InfoBase infoBase) throws YellowException {

        try {
            Path tmpOut = Files.createTempFile(UUID.randomUUID().toString(), ".out");

            Command command =
                    new Command().designer()
                            .infoBase(infoBase)
                            .dumpCfgList()
                            .allExtensions()
                            .out(tmpOut)
                            .build();

            startProcess(yellow, command);

            if (Files.exists(tmpOut)) {
                try (Stream<String> stream = Files.lines(tmpOut, StandardCharsets.UTF_8)) {
                    Files.delete(tmpOut);

                    return stream
                            .map(item -> new Extension(item.replaceAll("[\uFEFF-\uFFFF]", "").trim()))
                            .collect(Collectors.toList());
                }
            }

            return Collections.emptyList();

        } catch (IOException ex) {
            throw new YellowException(ex);
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
     * Запуск конфигуратора 1С:Предприятие в пакетном режиме
     *
     * @param yellow Экземпляр "толстого" клиента 1С:Предприятие
     * @param command Аргументы запуска
     * @throws YellowException Исключение при работе c толстым клиентом 1С:Предприятие
     */
    private void startProcess(Yellow yellow, Command command) throws YellowException {
        startProcess(yellow, command.get());
    }

    /**
     * Запуск конфигуратора 1С:Предприятие в пакетном режиме
     *
     * @param yellow Экземпляр "толстого" клиента 1С:Предприятие
     * @param parameters Аргументы запуска строкой
     * @throws YellowException Исключение при работе c толстым клиентом 1С:Предприятие
     */
    private void startProcess(Yellow yellow, String parameters) throws YellowException {
        try {
            Path executable = yellow.getThickClient();

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(
                    isWindows() ? "cmd.exe" : "bash",
                    isWindows() ? "/c" : "-c",
                    executable + " " + parameters);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new YellowException("Exited with error code (THICK CLIENT): " + exitCode);
            }

        } catch (InterruptedException | IOException e) {
            throw new YellowException(e);
        }
    }

    /**
     * Класс для формирования аргументов запуска 1С:Предприятие в пакетной режиме
     */
    public static class Command {

        private final List<String> args = new ArrayList<>();

        public CommandBuilder designer() {
            this.getArgs().add("DESIGNER");
            return new CommandBuilder(this);
        }

        public String get() {
            return
                    String.join(" ", this.getArgs());
        }

        private List<String> getArgs() {
            return args;
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
             * Информация об информационной базе
             * @param infoBase Описание информационной базы
             * @return Построитель команды
             */
            public CommandBuilder infoBase(InfoBase infoBase) {
                if (infoBase.isFileBase()) {
                    fileInfoBase(infoBase.getPath());
                } else {
                    // TODO: Клиент-серверная ИБ
                }

                login(infoBase.getLogin());
                password(infoBase.getPassword());

                return this;
            }

            /**
             * Расположение файловой информационной базы
             * @param infoBasePath Расположение информационной базы
             * @return Построитель команды
             */
            public CommandBuilder fileInfoBase(Path infoBasePath) {
                this.command.getArgs().add("/F \"" + infoBasePath + "\"");
                return this;
            }

            /**
             * Имя пользователя информационной базы (администратора)
             * @param login Имя пользователя
             * @return Построитель команды
             */
            public CommandBuilder login(String login) {
                if (login != null && !login.isBlank()) {
                    this.command.getArgs().add("/N \"" + login + "\"");
                }
                return this;
            }

            /**
             * Пароль пользователя информационной базы
             * @param password Пароль
             * @return Построитель команды
             */
            public CommandBuilder password(String password) {
                if (password != null && !password.isBlank()) {
                    this.command.getArgs().add("/P \"" + password + "\"");
                }
                return this;
            }

            /**
             * Выгрузка конфигурации в XML-файлы
             * @param target Распложение XML-файлов
             * @return Построитель команды
             */
            public CommandBuilder dumpConfigToFiles(Path target) {
                this.command.getArgs().add("/DumpConfigToFiles \"" + target + "\"");
                return this;
            }

            /**
             * Выгрузка имен расширений / конфигурации
             * @return Построитель команды
             */
            public CommandBuilder dumpCfgList() {
                this.command.getArgs().add("/DumpDBCfgList");
                return this;
            }

            /**
             * Информация о хранилище конфигурации
             * @param cr Хранилище конфигурации
             * @return Построитель команды
             */
            public CommandBuilder configurationRepository(ConfigurationRepository cr) {
                configurationRepositoryConnectionString(cr.getConnectionString());
                configurationRepositoryLogin(cr.getLogin());
                configurationRepositoryPassword(cr.getPassword());

                return this;
            }

            /**
             * Строка подключения к хранилищу конфигурации
             * @param connectionString Строка подключения
             * @return Построитель команды
             */
            public CommandBuilder configurationRepositoryConnectionString(String connectionString) {
                this.command.getArgs().add("/ConfigurationRepositoryF \"" + connectionString + "\"");
                return this;
            }

            /**
             * Имя пользователя хранилища конфигуации
             * @param login Имя пользователя
             * @return Построитель команды
             */
            public CommandBuilder configurationRepositoryLogin(String login) {
                if (login != null && !login.isBlank()) {
                    this.command.getArgs().add("/ConfigurationRepositoryN \"" + login + "\"");
                }
                return this;
            }

            /**
             * Пароль пользователя хранилища конфигурации
             * @param password Пароль
             * @return Построитель команды
             */
            public CommandBuilder configurationRepositoryPassword(String password) {
                if (password != null && !password.isBlank()) {
                    this.command.getArgs().add("/ConfigurationRepositoryP \"" + password + "\"");
                }
                return this;
            }

            /**
             * Обновление конфигурации из хранилища конфигурации
             * @return Построитель команды
             */
            public CommandBuilder configurationRepositoryUpdateCfg() {
                this.command.getArgs().add("/ConfigurationRepositoryUpdateCfg");
                return this;
            }

            /**
             * Опция, указывающая на режим работы с расширением
             * @param extension Расширение
             * @return Построитель команды
             */
            public CommandBuilder extension(Extension extension) {
                this.command.getArgs().add("-Extension " + extension.getName());
                return this;
            }

            /**
             * Опция, указывающая на режим работы со всеми расширениями информационной базы
             * @return Построитель команды
             */
            public CommandBuilder allExtensions() {
                this.command.getArgs().add("-AllExtensions");
                return this;
            }

            /**
             * Хранилище конфигурации: Если при пакетном обновлении конфигурации из хранилища должны быть получены
             * новые объекты конфигурации или удалиться существующие, указание этого параметра свидетельствует \
             * о подтверждении пользователем описанных выше операций.
             * Если параметр не указан ‑ действия выполнены не будут.
             * @return Построитель команды
             */
            public CommandBuilder force() {
                this.command.getArgs().add("-force");
                return this;
            }

            /**
             * Подавляет стартовые сообщения
             * @return Построитель команды
             */
            public CommandBuilder disableStartupDialogs() {
                this.command.getArgs().add("/DisableStartupDialogs");
                return this;
            }

            /**
             * Вывод в файл
             * @param out Расположение файла вывода
             * @return Построитель команды
             */
            public CommandBuilder out(Path out) {
                this.command.getArgs().add("/Out \"" + out + "\"");
                return this;
            }

            public Command build() {
                return this.command;
            }
        }
    }
}
