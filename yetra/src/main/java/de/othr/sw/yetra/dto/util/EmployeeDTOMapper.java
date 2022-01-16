package de.othr.sw.yetra.dto.util;

import de.othr.sw.yetra.dto.UserDTO;
import de.othr.sw.yetra.entity.Employee;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class EmployeeDTOMapper implements DTOMapper<Employee, UserDTO> {

    @Override
    public UserDTO toDTO(Employee employee) {
        UserDTO dto = new UserDTO();
        dto.setUsername(employee.getUsername());
        //dto.setPassword(employee.getPassword());
        return dto;
    }

    @Override
    public Employee fromDTO(UserDTO dto) {
        Employee employee = new Employee();
        employee.setUsername(dto.getUsername());
        employee.setPassword(dto.getPassword());
        return employee;
    }
}
