package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.TradingPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserServiceIF {
    Employee createEmployee(Employee employee);
    Employee getEmployee(long id);
    Page<Employee> getEmployees(Pageable pageable);

    TradingPartner createTradingPartner(TradingPartner tradingPartner);
    TradingPartner getTradingPartner(long id);
    Page<TradingPartner> getTradingPartners(Pageable pageable);
}
