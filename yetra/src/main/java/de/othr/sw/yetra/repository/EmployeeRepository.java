package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.Employee;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.PagingAndSortingRepository;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Scope(SCOPE_SINGLETON)
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
