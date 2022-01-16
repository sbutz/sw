package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
