package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
