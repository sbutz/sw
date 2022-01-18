package de.othr.sw.yetra.setup;

import com.google.common.collect.Sets;
import de.othr.sw.yetra.entity.UserPrivilege;
import de.othr.sw.yetra.entity.UserRole;
import de.othr.sw.yetra.repository.UserPrivilegeRepository;
import de.othr.sw.yetra.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component(value = UserRoles.component)
@Scope(SCOPE_SINGLETON)
public class UserRoles {

    public static final String component = "UserRoleSetup";

    @Autowired
    private Logger logger;

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
        logger.info("Creating user roles...");

        UserPrivilege ordersRead        = new UserPrivilege("ORDERS_READ");
        UserPrivilege ordersWrite       = new UserPrivilege("ORDERS_WRITE");
        UserPrivilege sharesRead        = new UserPrivilege("SHARES_READ");
        UserPrivilege sharesWrite       = new UserPrivilege("SHARES_WRITE");
        UserPrivilege usersRead         = new UserPrivilege("USERS_READ");
        UserPrivilege usersWrite        = new UserPrivilege("USERS_WRITE");
        UserPrivilege transactionsRead  = new UserPrivilege("TRANSACTIONS_READ");

        UserRole adminRole = new UserRole("ROLE_ADMIN");
        adminRole.setPrivileges(Sets.newHashSet(ordersRead, ordersWrite, sharesRead, sharesWrite, usersRead, usersWrite, transactionsRead));
        userRoleRepository.save(adminRole);

        UserRole tradingPartnerRole = new UserRole("ROLE_TRADING_PARTNER");
        tradingPartnerRole.setPrivileges(Sets.newHashSet(ordersRead, ordersWrite, sharesRead));
        userRoleRepository.save(tradingPartnerRole);
    }
}
