package de.othr.sw.yetra.setup;

import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repository.UserRepository;
import de.othr.sw.yetra.service.impl.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component(value = Users.component)
@DependsOn(UserRoles.component)
@Scope(SCOPE_SINGLETON)
public class Users {

    public static final String component = "UserSetup";

    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void createUsers() {
        logger.info("Creating users...");
        //TODO: inject iban
        //TODO: inject admin credentials?
        createUser("admin", "123", "ROLE_ADMIN", "DE0123456789");
        createUser("import", "123", "ROLE_TRADING_PARTNER", "DE0123456789");
        createUser("bot", "123", "ROLE_TRADING_PARTNER", "DE0123456789");
    }

    public void createUser(String username, String password, String role, String iban) {
        try {
            userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(new UserRole(role));
            user.setBankAccount(new BankAccount(iban));
            userService.createUser(user);
        }
    }
}
