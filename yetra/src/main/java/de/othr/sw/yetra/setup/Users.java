package de.othr.sw.yetra.setup;

import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.TradingPartner;
import de.othr.sw.yetra.repository.UserRepository;
import de.othr.sw.yetra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component(value = Users.component)
@DependsOn(UserRoles.component)
public class Users {

    public static final String component = "UserSetup";

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostConstruct
    public void createUsers() {
        createEmployee("admin", "123");
        createTradingPartner("import", "123", "DE0123456789", null);
        //TODO: remove
        createTradingPartner("ynvest", "123", "DE0123456789", "sw_andreas_huber_queue_ynvest_orders");
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
