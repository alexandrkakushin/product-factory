import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pf.yellow.BatchMode;
import ru.pf.yellow.InfoBase;
import ru.pf.yellow.Yellow;
import ru.pf.yellow.YellowException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест пакетного режима 1С:Предприятие
 * @author a.kakushin
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BatchModeTest {

    static Path pathFileBase;
    static Path app;

    @Autowired
    private BatchMode batchMode;

    @BeforeAll
    static void init() throws IOException {
        app = Path.of("/opt/1cv8/x86_64/8.3.18.1616/1cv8");
        pathFileBase = Files.createTempDirectory("temp_ib_" + UUID.randomUUID());
        Files.createDirectories(pathFileBase);
    }

    @Test
    @Order(1)
    void contextLoads() {
        assertNotNull(batchMode);
    }

    @Test
    @Order(2)
    void createFileInfoBaseTest() throws YellowException {
        if (Files.exists(app)) {
            Yellow yellow = new Yellow(app);
            batchMode.createFileInfoBase(yellow, pathFileBase);
            assertTrue(Files.exists(pathFileBase.resolve("1Cv8.1CD")));
        }
    }

    @Test
    @Order(3)
    void dumpConfigToFilesTest() throws YellowException, IOException {
        if (Files.exists(app)) {
            Yellow yellow = new Yellow(app);
            Path pathXml = Files.createTempDirectory("temp_xml_" + UUID.randomUUID());
            batchMode.dumpConfigToFiles(yellow, new InfoBase(pathFileBase), pathXml);
            assertTrue(Files.exists(pathXml.resolve("Configuration.xml")));
            deleteDirectory(pathXml);
        }
    }

    @Test
    @Order(4)
    void configurationRepositoryUpdateCfgTest() {
        BatchMode.Command command =
                new BatchMode.Command().designer()
                        .configurationRepositoryConnectionString("tcp://localhost/test")
                        .configurationRepositoryLogin("login")
                        .configurationRepositoryPassword("password")
                        .configurationRepositoryUpdateCfg()
                        .build();

        assertEquals(command.get(), "DESIGNER /ConfigurationRepositoryF \"tcp://localhost/test\" /ConfigurationRepositoryN \"login\" /ConfigurationRepositoryP \"password\" /ConfigurationRepositoryUpdateCfg");
    }

    @AfterAll
    static void cleanUp() throws IOException {
        deleteDirectory(pathFileBase);
    }

    static void deleteDirectory(Path dir) throws IOException {
        if (Files.exists(dir)) {
            Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @SpringBootApplication
    @ComponentScan(basePackages = "ru.pf.yellow")
    static class TestConfiguration {}
}
