package de.othr.sw.yetra.setup;

import com.google.common.collect.Sets;
import de.othr.sw.yetra.entity.UserPrivilege;
import de.othr.sw.yetra.entity.UserRole;
import de.othr.sw.yetra.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration()
@Scope(SCOPE_SINGLETON)
public class UserRoles {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private UserRole adminRole;

    private UserRole tradingPartnerRole;

    @PostConstruct
    public void createRoles() {
        adminRole = new UserRole("ROLE_ADMIN");
        adminRole.setPrivileges(Sets.newHashSet(
                new UserPrivilege("ORDERS_READ"),
                new UserPrivilege("ORDERS_WRITE"),
                new UserPrivilege("SHARES_READ"),
                new UserPrivilege("SHARES_WRITE"),
                new UserPrivilege("USERS_READ"),
                new UserPrivilege("USERS_WRITE"),
                new UserPrivilege("TRANSACTIONS_READ")
        ));
        adminRole = userRoleRepository.save(adminRole);
        tradingPartnerRole = new UserRole("ROLE_TRADING_PARTNER");
        tradingPartnerRole.setPrivileges(Sets.newHashSet(
                new UserPrivilege("ORDERS_READ"),
                new UserPrivilege("ORDERS_WRITE"),
                new UserPrivilege("SHARES_READ")
        ));
        tradingPartnerRole = userRoleRepository.save(tradingPartnerRole);
    }

    @Bean("admin")
    @Scope(SCOPE_SINGLETON)
    public UserRole getAdminRole() {
        return adminRole;
    }

    @Bean("tradingPartner")
    @Scope(SCOPE_SINGLETON)
    public UserRole getTradingPartnerRole() {
        return tradingPartnerRole;
    }
}
