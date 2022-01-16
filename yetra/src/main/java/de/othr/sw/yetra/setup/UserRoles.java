package de.othr.sw.yetra.setup;

import com.google.common.collect.Sets;
import de.othr.sw.yetra.entity.UserPrivilege;
import de.othr.sw.yetra.entity.UserRole;
import de.othr.sw.yetra.repository.UserPrivilegeRepository;
import de.othr.sw.yetra.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Component(value = UserRoles.component)
public class UserRoles {

    public static final String component = "UserRoleSetup";

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserPrivilegeRepository userPrivilegeRepository;

    @PostConstruct
    /*
     * @Transactional does not work on @PostConstruct, because the interceptor proxy starts working after
     * the bean is fully initialized.
     * As a result some manual calls to save() are necessary.
     */
    public void createUserRoles() {
        UserPrivilege ordersRead        = getOrCreatePrivilege("ORDERS_READ");
        UserPrivilege ordersWrite       = getOrCreatePrivilege("ORDERS_WRITE");
        UserPrivilege sharesRead        = getOrCreatePrivilege("SHARES_READ");
        UserPrivilege sharesWrite       = getOrCreatePrivilege("SHARES_WRITE");
        UserPrivilege usersRead         = getOrCreatePrivilege("USERS_READ");
        UserPrivilege usersWrite        = getOrCreatePrivilege("USERS_WRITE");
        UserPrivilege transactionsRead  = getOrCreatePrivilege("TRANSACTIONS_READ");

        UserRole adminRole = getOrCreateRole("ROLE_ADMIN");
        adminRole.setPrivileges(Sets.newHashSet(ordersRead, ordersWrite, sharesRead, sharesWrite, usersRead, usersWrite, transactionsRead));
        userRoleRepository.save(adminRole);

        UserRole tradingPartnerRole = getOrCreateRole("ROLE_TRADING_PARTNER");
        tradingPartnerRole.setPrivileges(Sets.newHashSet(ordersRead, ordersWrite, sharesRead));
        userRoleRepository.save(tradingPartnerRole);
    }

    private UserPrivilege getOrCreatePrivilege(String name) {
        Optional<UserPrivilege> p = userPrivilegeRepository.findById(name);
        if (p.isPresent())
            return p.get();
        else
            return userPrivilegeRepository.save(new UserPrivilege(name));
    }

    private UserRole getOrCreateRole(String name) {
        Optional<UserRole> p = userRoleRepository.findById(name);
        if (p.isPresent())
            return p.get();
        else
            return userRoleRepository.save(new UserRole(name));
    }
}
