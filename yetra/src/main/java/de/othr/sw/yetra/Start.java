package de.othr.sw.yetra;

import com.google.common.collect.Sets;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repo.ShareRepository;

import de.othr.sw.yetra.repo.UserPrivilegeRepository;
import de.othr.sw.yetra.repo.UserRoleRepository;
import de.othr.sw.yetra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class Start implements CommandLineRunner {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserPrivilegeRepository userPrivilegeRepository;

    @Autowired
    UserService userService;

    @Autowired
    ShareRepository shareRepo;

    @Override
    @Transactional
    public void run(String... args) {
        UserPrivilege ordersRead        = getOrCreatePrivilege("ORDERS_READ");
        UserPrivilege ordersWrite       = getOrCreatePrivilege("ORDERS_WRITE");
        UserPrivilege sharesRead        = getOrCreatePrivilege("SHARES_READ");
        UserPrivilege sharesWrite       = getOrCreatePrivilege("SHARES_WRITE");
        UserPrivilege usersRead         = getOrCreatePrivilege("USERS_READ");
        UserPrivilege usersWrite        = getOrCreatePrivilege("USERS_WRITE");
        UserPrivilege transactionsRead  = getOrCreatePrivilege("TRANSACTIONS_WRITE");
        UserRole adminRole = getOrCreateRole("ROLE_ADMIN");
        adminRole.addPrivileges(Sets.newHashSet(ordersRead, ordersWrite, sharesRead, sharesWrite, usersRead, usersWrite, transactionsRead));
        UserRole tradingPartnerRole = getOrCreateRole("ROLE_TRADING_PARTNER");
        tradingPartnerRole.addPrivileges(Sets.newHashSet(ordersRead, ordersWrite, sharesRead));

        //TODO: testnutzer in readme vermerken
        //TODO: min passwort length?
        Employee e = new Employee();
        e.setUsername("admin");
        e.setPassword("123");
        userService.createEmployee(e);
        TradingPartner t = new TradingPartner();
        t.setUsername("eBank");
        t.setPassword("123");
        t.setBillingBankAccount(new BankAccount("DE0123456789"));
        userService.createTradingPartner(t);

        //TODO: Mit ordentlichem Aktienimport ersetzen
        shareRepo.save(new Share("DE0005190003", "BMW AG", 58.55f));
        shareRepo.save(new Share("DE0007664039", "Volkswagen Group", 180.0f));
        shareRepo.save(new Share("US88160R1014", "Telsa, Inc.", 832.90f));
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
