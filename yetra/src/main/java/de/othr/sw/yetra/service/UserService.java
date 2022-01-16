package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.TradingPartner;
import de.othr.sw.yetra.repo.EmployeeRepository;
import de.othr.sw.yetra.repo.UserRepository;
import de.othr.sw.yetra.repo.TradingPartnerRepository;
import de.othr.sw.yetra.repo.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceIF, UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private TradingPartnerRepository tradingPartnerRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Employee createEmployee(Employee employee) {
        //TODO: id should be 0
        if (userRepo.existsById(employee.getId())
                || userRepo.findUserByUsername(employee.getUsername()).isPresent())
            throw new ServiceException(409, "User already exists");

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(userRoleRepository.findById("ROLE_ADMIN").get());
        return employeeRepo.save(employee);
    }

    @Override
    public Employee getEmployee(long id) {
        return employeeRepo
                .findById(id)
                .orElseThrow(() -> {
                    throw new ServiceException(404, "Employee not found");
                });
    }

    @Override
    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    @Override
    public TradingPartner createTradingPartner(TradingPartner tradingPartner) {
        //TODO: id should be 0
        if (userRepo.existsById(tradingPartner.getId())
                || userRepo.findUserByUsername(tradingPartner.getUsername()).isPresent())
            throw new ServiceException(409, "Trading Partner already exists");

        tradingPartner.setPassword(passwordEncoder.encode(tradingPartner.getPassword()));
        tradingPartner.setRole(userRoleRepository.findById("ROLE_TRADING_PARTNER").get());
        return tradingPartnerRepository.save(tradingPartner);
    }

    @Override
    public TradingPartner getTradingPartner(long id) {
        return tradingPartnerRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new ServiceException(404, "Trading Partner not found");
                });
    }

    @Override
    public Page<TradingPartner> getTradingPartners(Pageable pageable) {
        return tradingPartnerRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findUserByUsername(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User (name=" + username + ") not found");
                });
    }
}
