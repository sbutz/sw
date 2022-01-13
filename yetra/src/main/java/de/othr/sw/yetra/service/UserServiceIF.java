package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.TradingPartner;

public interface UserServiceIF {
    Employee createEmployee(Employee employee);
    Employee getEmployee(long id);
    Iterable<Employee> getEmployees();

    TradingPartner createTradingPartner(TradingPartner tradingPartner);
    TradingPartner getTradingPartner(long id);
    Iterable<TradingPartner> getTradingPartners();
}
