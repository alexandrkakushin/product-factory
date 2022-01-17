package ru.pf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pf.entity.Os;
import ru.pf.repository.OsRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Тесты CRUD-операций (репозиторий для работы со списком операционных систем)
 * @author a.kakushin
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class OsRepositoryTests {

    /**
     * Менеджер для работы с репозиториями
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
    void testCreate() {
        Os testOs = new Os("test");
        entityManager.persist(testOs);
        entityManager.flush();

        assertTrue(osRepository.findByName(testOs.getName()).isPresent());
    }
}
