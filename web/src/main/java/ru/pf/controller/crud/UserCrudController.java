package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.auth.Role;
import ru.pf.entity.auth.User;
import ru.pf.repository.PfCrudRepository;
import ru.pf.repository.auth.RoleCrudRepository;
import ru.pf.repository.auth.UserCrudRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * CRUD-контроллер для управления пользователями
 * @author a.kakushin
 */
@Controller
@RequestMapping(UserCrudController.URL)
public class UserCrudController implements PfCrudController<User> {

    static final String URL = "admin/users";

    /**
     * Репозиторий для управления пользователя в БД
     */
    @Autowired
    private UserCrudRepository userRepository;

    /**
     * Репозиторий для управления ролями в БД
     */
    @Autowired
    private RoleCrudRepository roleRepository;

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getTemplateItem() {
        return "user-item";
    }

    @Override
    public String getName() {
        return "Пользователи";
    }

    @Override
    public PfCrudRepository<User> getRepository() {
        return this.userRepository;
    }

    @Override
    public String submit(User entity) throws SubmitException {
        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        if (role.isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(role.get());
            entity.setRoles(roles);
        } else {
            throw new SubmitException("При записи пользователя возникла ошибка. Роль не найдена");
        }
        return PfCrudController.super.submit(entity);
    }
}
