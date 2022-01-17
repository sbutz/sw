package de.othr.sw.yetra.controller.mvc;

import de.othr.sw.yetra.entity.User;
import de.othr.sw.yetra.entity.UserRole;
import de.othr.sw.yetra.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Controller
@RequestMapping("/users")
@Scope(SCOPE_SINGLETON)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserServiceIF userService;

    @Autowired
    @Qualifier("pageSize")
    private int pageSize;

    @GetMapping(value = "")
    public String getUsers(Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
            model.addAttribute("users", userService.getUsers(PageRequest.of(page, pageSize)));
        return "userTable";
    }

    @GetMapping(value = "/create")
    public String getUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", this.getRoleOptions());
        model.addAttribute("validated", false);
        return "userForm";
    }

    @PostMapping(value = "/create")
    public String createUser(Model model,
                             @Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", this.getRoleOptions());
            model.addAttribute("validated", true);
            return "userForm";
        } else {
            userService.createUser(user);
            return "redirect:/users";
        }
    }

    private Map<String, String> getRoleOptions() {
        SortedMap<String, String> roles = new TreeMap<>();
        for (UserRole s : userService.getUserRoles()) {
            roles.put(s.getName(), s.getHumanReadableName());
        }
        return roles;
    }
}