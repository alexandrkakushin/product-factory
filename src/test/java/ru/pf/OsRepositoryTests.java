package ru.pf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pf.entity.Os;
import ru.pf.repository.OsRepository;

import static org.junit.Assert.assertTrue;

/**
 * Тесты CRUD-операций (репозиторий для работы со списком операционных систем)
 * @author a.kakushin
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OsRepositoryTests {

    /**
     * Менеджер для работы с репозито
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * Репозиторий для работы со списком ОС
     */
    @Autowired
    private OsRepository osRepository;

    /**
     * Тест создания записи в таблице БД
     */
    @Test
    public void testCreate() {
        Os testOs = new Os("test");
        entityManager.persist(testOs);
        entityManager.flush();

        assertTrue(osRepository.findByName(testOs.getName()).isPresent());
    }
}
