package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
