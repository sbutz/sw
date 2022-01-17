package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Scope(SCOPE_SINGLETON)
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
