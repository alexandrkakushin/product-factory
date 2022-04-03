package ru.pf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pf.repository.*;
import ru.pf.repository.auth.RoleCrudRepository;
import ru.pf.repository.auth.UserCrudRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = JpaTestConfig.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {"spring.liquibase.change-log=classpath:db/changelog/db.changelog-root.xml"})
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=validate"})
class InitTest {

    @Autowired
    private RoleCrudRepository roleCrudRepository;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    private CrCrudRepository crCrudRepository;

    @Autowired
    private DesignerCrudRepository designerCrudRepository;

    @Autowired
    private GitCrudRepository gitCrudRepository;

    @Autowired
    private InfoBaseCrudRepository infoBaseCrudRepository;

    @Autowired
    private OsCrudRepository osCrudRepository;

    @Autowired
    private ProjectsCrudRepository projectsCrudRepository;

    @Autowired
    private PropertiesCrudRepository propertiesCrudRepository;

    @Autowired
    private ServersCrudRepository serversCrudRepository;

    @Autowired
    private ServicesCrudRepository servicesCrudRepository;

    @Test
    void injectTest() {
        assertNotNull(roleCrudRepository);
        assertNotNull(userCrudRepository);
        assertNotNull(crCrudRepository);
        assertNotNull(designerCrudRepository);
        assertNotNull(gitCrudRepository);
        assertNotNull(infoBaseCrudRepository);
        assertNotNull(osCrudRepository);
        assertNotNull(projectsCrudRepository);
        assertNotNull(propertiesCrudRepository);
        assertNotNull(serversCrudRepository);
        assertNotNull(servicesCrudRepository);
    }
}
