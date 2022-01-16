package de.othr.sw.yetra.setup;

import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.TradingPartner;
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
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void createUsers() {
        logger.info("Creating users...");
        createEmployee("admin", "123");
        createTradingPartner("import", "123", "DE0123456789", null);
        createTradingPartner("bot", "123", "DE0123456789", null);
    }

    public void createEmployee(String username, String password) {
        try {
            userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            Employee employee = new Employee();
            employee.setUsername(username);
            employee.setPassword(password);
            userService.createEmployee(employee);
        }
    }

    public void createTradingPartner(String username, String password, String iban, String channel) {
        try {
            userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            TradingPartner partner = new TradingPartner();
            partner.setUsername(username);
            partner.setPassword(password);
            partner.setBillingBankAccount(new BankAccount(iban));
            partner.setNotifyChannelName(channel);
            userService.createTradingPartner(partner);
        }
    }
}
