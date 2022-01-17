package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.entity.User;
import de.othr.sw.yetra.entity.UserRole;
import de.othr.sw.yetra.repository.UserRepository;
import de.othr.sw.yetra.repository.UserRoleRepository;
import de.othr.sw.yetra.service.UserServiceIF;
import de.othr.sw.yetra.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Service
@Scope(SCOPE_SINGLETON)
public class UserService implements UserServiceIF, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserRoleRepository userRoleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    Validator validator;

    @Override
    public User createUser(User user) throws ServiceException {
        if (userRepo.findUserByUsername(user.getUsername()).isPresent())
            //TODO: specialized excpetions
            //no http codes in service code
            throw new ConflictException("User already exists");

        Set<ConstraintViolation<User>> errors = validator.validate(user);
        if (!errors.isEmpty())
            throw new InvalidEntityException(errors.iterator().next().getMessage());

        Optional<UserRole> role = userRoleRepo.findById(user.getRole().getName());
        if (role.isEmpty())
            throw new NotFoundException("User Role does not exists");
        else
            user.setRole(role.get());

        user.setId(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User getUser(long id) {
        return userRepo
                .findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("User not found");
                });
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public Iterable<UserRole> getUserRoles() {
        return userRoleRepo.findAll();
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
