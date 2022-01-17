package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.User;
import de.othr.sw.yetra.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserServiceIF {
    User createUser(User user);
    Page<User> getUsers(Pageable pageable);
    Iterable<UserRole> getUserRoles();
}
