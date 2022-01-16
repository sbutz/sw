package de.othr.sw.yetra.controller.mvc;

import de.othr.sw.yetra.dto.TradingPartnerDTO;
import de.othr.sw.yetra.dto.UserDTO;
import de.othr.sw.yetra.dto.util.DTOMapper;
import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.TradingPartner;
import de.othr.sw.yetra.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Controller
@Scope(SCOPE_SINGLETON)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserServiceIF userService;

    @Autowired
    private DTOMapper<TradingPartner, TradingPartnerDTO> tradingPartnerMapper;

    @Autowired
    private DTOMapper<Employee, UserDTO> employeeMapper;

    @GetMapping(value = "/trading-partners")
    public String getTradingPartners(Model model,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
            model.addAttribute("tradingPartners", userService.getTradingPartners(PageRequest.of(page, 20)));
        return "tradingPartnerTable";
    }

    @GetMapping(value = "/trading-partners/create")
    public String getTradingPartnerForm(Model model) {
        model.addAttribute("tradingPartner", new TradingPartnerDTO());
        model.addAttribute("validated", false);
        return "tradingPartnerForm";
    }

    @PostMapping(value = "/trading-partners/create")
    public String createTradingPartner(Model model,
                                      @Valid @ModelAttribute("tradingPartner") TradingPartnerDTO tradingPartner,
                                      BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tradingPartner", tradingPartner);
            model.addAttribute("validated", true);
            return "tradingPartnerForm";
        } else {
            userService.createTradingPartner(tradingPartnerMapper.fromDTO(tradingPartner));
            return "redirect:/trading-partners";
        }
    }

    @GetMapping(value = "/employees")
    public String getEmployees(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
        model.addAttribute("employees", userService.getEmployees(PageRequest.of(page, 20)));
        return "employeesTablehtml";
    }

    @GetMapping(value = "/employees/create")
    public String getEmployeeForm(Model model) {
        model.addAttribute("employee", new UserDTO());
        model.addAttribute("validated", false);
        return "employeeForm";
    }

    @PostMapping(value = "/employees/create")
    public String createEmployee(Model model,
                                     @Valid @ModelAttribute("employee") UserDTO employee,
                                     BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            model.addAttribute("validated", true);
            return "employeeForm";
        } else {
            userService.createEmployee(employeeMapper.fromDTO(employee));
            return "redirect:/employees";
        }
    }
}