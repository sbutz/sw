package de.othr.sw.yetra.setup;

import com.google.common.collect.Sets;
import de.othr.sw.yetra.entity.UserPrivilege;
import de.othr.sw.yetra.entity.UserRole;
import de.othr.sw.yetra.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration()
@Scope(SCOPE_SINGLETON)
public class UserRoles {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Bean("admin")
    public UserRole getAdminRole() {
        UserRole adminRole = new UserRole("ROLE_ADMIN");
        adminRole.setPrivileges(Sets.newHashSet(
                new UserPrivilege("ORDERS_READ"),
                new UserPrivilege("ORDERS_WRITE"),
                new UserPrivilege("SHARES_READ"),
                new UserPrivilege("SHARES_WRITE"),
                new UserPrivilege("USERS_READ"),
                new UserPrivilege("USERS_WRITE"),
                new UserPrivilege("TRANSACTIONS_READ")
        ));
        return userRoleRepository.save(adminRole);
    }

    @Bean("tradingPartner")
    public UserRole getTradingPartnerRole() {
        UserRole tradingPartnerRole = new UserRole("ROLE_TRADING_PARTNER");
        tradingPartnerRole.setPrivileges(Sets.newHashSet(
                new UserPrivilege("ORDERS_READ"),
                new UserPrivilege("ORDERS_WRITE"),
                new UserPrivilege("SHARES_READ")
        ));
        return userRoleRepository.save(tradingPartnerRole);
    }
}
