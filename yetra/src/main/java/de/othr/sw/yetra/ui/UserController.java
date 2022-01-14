package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.dto.TradingPartnerDTO;
import de.othr.sw.yetra.dto.UserDTO;
import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.TradingPartner;
import de.othr.sw.yetra.entity.UserRole;
import de.othr.sw.yetra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//TODO: all actions only as admin
@Controller
public class UserController {

    @Autowired
    UserService userService;

    //TODO: pagination
    //TODO: @Get/PostMapping() verwenden
    @RequestMapping(value = "/trading-partners", method = RequestMethod.GET)
    public String getTradingPartners(Model model) {
        model.addAttribute("tradingPartners", userService.getTradingPartners());
        return "tradingPartnerList";
    }

    @RequestMapping(value = "/trading-partners/create", method = RequestMethod.GET)
    public String getTradingPartnerForm(Model model) {
        model.addAttribute("tradingPartner", new TradingPartnerDTO());
        model.addAttribute("validated", false);
        return "tradingPartnerForm";
    }

    @RequestMapping(value = "/trading-partners/create", method = RequestMethod.POST)
    public String handelspartnerAnlegen(Model model,
                                      @Valid @ModelAttribute("tradingPartner") TradingPartnerDTO tradingPartner,
                                      BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tradingPartner", tradingPartner);
            model.addAttribute("validated", true);
            return "tradingPartnerForm";
        } else {
            //TODO: factory method TradingPartner.fromDTO()
            TradingPartner tp = new TradingPartner();
            tp.setUsername(tradingPartner.getUsername());
            tp.setPassword(tradingPartner.getPassword());
            tp.setBillingBankAccount(new BankAccount(tradingPartner.getIban()));
            //TODO: methode anders benennen
            userService.createTradingPartner(tp);
            return "redirect:/trading-partners";
        }
    }

    //TODO: pagination
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String angestellteAnzeigen(Model model) {
        model.addAttribute("employees", userService.getEmployees());
        return "employeesList";
    }

    @RequestMapping(value = "/employees/create", method = RequestMethod.GET)
    public String angestellteAnlegen(Model model) {
        model.addAttribute("employee", new UserDTO());
        model.addAttribute("validated", false);
        return "employeeForm";
    }

    @RequestMapping(value = "/employees/create", method = RequestMethod.POST)
    public String angestellteAnlegen(Model model,
                                     @Valid @ModelAttribute("employee") UserDTO employee,
                                     BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            model.addAttribute("validated", true);
            return "employeeForm";
        } else {
            //TODO: factory method Employe.fromDTO()
            Employee e = new Employee();
            e.setUsername(employee.getUsername());
            e.setPassword(employee.getPassword());
            //TODO: methode anders benennen
            userService.createEmployee(e);
            return "redirect:/employees";
        }
    }
}