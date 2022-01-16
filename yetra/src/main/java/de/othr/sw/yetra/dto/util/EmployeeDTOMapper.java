package de.othr.sw.yetra.dto.util;

import de.othr.sw.yetra.dto.TradingPartnerDTO;
import de.othr.sw.yetra.dto.UserDTO;
import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.TradingPartner;
import org.springframework.stereotype.Component;

@Component
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
