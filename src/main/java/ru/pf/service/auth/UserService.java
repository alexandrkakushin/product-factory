package ru.pf.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pf.config.CredentialsProperties;
import ru.pf.entity.auth.Role;
import ru.pf.entity.auth.User;
import ru.pf.repository.auth.RoleRepository;
import ru.pf.repository.auth.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Сервис для работы с пользователями
 * @author a.kakushin
 */
@Service
public class UserService implements UserDetailsService {

    /**
     * Репозиторий для работы с пользователями
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Репозиторий для работы с ролями
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * Административный доступ
     */
    @Autowired
    CredentialsProperties credentialsProperties;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        } else {
            if (name.equalsIgnoreCase(credentialsProperties.getAdmin().getName())) {
                return admin();
            }  else {
                throw new UsernameNotFoundException("User not found");
            }
        }
    }

    private User admin() {
        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
        if (role.isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(role.get());

            return new User(
                    credentialsProperties.getAdmin().getName(),
                    credentialsProperties.getAdmin().getPassword(),
                    roles
            );
        }
        return null;
    }
}
