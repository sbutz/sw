package de.othr.sw.yetra.setup;

import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.service.impl.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${yetra.bank.account}")
    private String iban;

    @Value("${yetra.admin.password")
    private String adminPassword;

    @PostConstruct
    public void createUsers() {
        logger.info("Creating users...");

        createUser("admin", adminPassword, "ROLE_ADMIN", iban);
        createUser("import", "secret123", "ROLE_TRADING_PARTNER", iban);
        createUser("bot", "secret123", "ROLE_TRADING_PARTNER", iban);
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
