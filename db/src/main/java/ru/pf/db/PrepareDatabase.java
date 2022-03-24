package ru.pf.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.pf.entity.Os;
import ru.pf.entity.auth.Role;
import ru.pf.repository.OsCrudRepository;
import ru.pf.repository.auth.RoleCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Подготовка таблиц базы данных
 * @author a.kakushin
 */
@Component
public class PrepareDatabase implements ApplicationRunner {

    /**
     * Репозиторий для управления операционными системами
     */
    @Autowired
    OsCrudRepository osRepository;

    /**
     * Репозиторий для управления ролями
     */
    @Autowired
    RoleCrudRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {
        prepareOs();
        prepareRoles();
    }

    /**
     * Заполнение таблицы "Операционные системы" (OS)
     */
    private void prepareOs() {
        List<String> list = new ArrayList<>();
        list.add("Ubuntu Server 18.04 LTS");
        list.add("Ubuntu Server 20.04 LTS");

        osRepository.saveAll(
            list.stream()
                    .filter(p -> osRepository.findByName(p).isEmpty())
                    .map(Os::new)
                    .collect(Collectors.toList())
        );
    }

    /**
     * Заполнение таблицы "Роли" (ROLES)
     */
    private void prepareRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        roleRepository.saveAll(
            roles.stream()
                .filter(p -> roleRepository.findByName(p).isEmpty())
                .map(Role::new)
                .collect(Collectors.toList())
        );
    }
}