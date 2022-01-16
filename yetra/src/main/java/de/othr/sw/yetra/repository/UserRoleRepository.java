package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.UserRole;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Scope(SCOPE_SINGLETON)
public interface UserRoleRepository extends CrudRepository<UserRole, String> {
}
