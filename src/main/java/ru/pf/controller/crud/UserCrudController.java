package ru.pf.controller.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pf.entity.auth.Role;
import ru.pf.entity.auth.User;
import ru.pf.repository.PfRepository;
import ru.pf.repository.auth.RoleRepository;
import ru.pf.repository.auth.UserRepository;

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
    private UserRepository userRepository;

    /**
     * Репозиторий для управления ролями в БД
     */
    @Autowired
    private RoleRepository roleRepository;

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
    public PfRepository<User> getRepository() {
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
        User saved = getRepository().save(entity);
        return "redirect:/" + getUrl() + "/" + saved.getId();
    }
}
