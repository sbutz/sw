package de.othr.sw.yetra.setup;

import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.service.ServiceException;
import de.othr.sw.yetra.service.UserServiceIF;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration()
@Scope(SCOPE_SINGLETON)
public class Users {

    @Autowired
    private Logger logger;

    @Autowired
    private UserServiceIF userService;

    @Autowired
    @Qualifier("admin")
    private UserRole adminRole;

    @Autowired
    @Qualifier("tradingPartner")
    private UserRole tradingPartnerRole;

    @Value("${yetra.bank.account}")
    private String iban;

    @Value("${yetra.admin.password")
    private String adminPassword;

    @PostConstruct
    public void createUsers() {
        logger.info("Creating admin user...");
        getOrCreateUser("admin", adminPassword, adminRole, iban);
    }

    @Bean("import")
    public User getImport() {
        return getOrCreateUser("import", "secret123", tradingPartnerRole, iban);
    }

    @Bean("bot")
    public User getBot() {
        return getOrCreateUser("bot", "secret123", tradingPartnerRole, iban);
    }

    private User getOrCreateUser(String username, String password, UserRole role, String iban) {
        try {
            return userService.getUser(username);
        } catch (ServiceException e) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setBankAccount(new BankAccount(iban));
            return userService.createUser(user);
        }
    }
}
