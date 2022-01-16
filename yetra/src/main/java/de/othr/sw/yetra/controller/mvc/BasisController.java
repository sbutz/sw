package de.othr.sw.yetra.controller.mvc;

import de.othr.sw.yetra.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BasisController {
    @GetMapping(value = "/")
    public String getHomepage() {
        return "homepage";
    }

    @GetMapping(value = "/login")
    public String anmeldeFormularAnzeigen(
            Model model,
            @RequestParam(name = "error", defaultValue = "false") boolean error
    ) {
        model.addAttribute("user", new Employee());
        model.addAttribute("validated", error);
        return "loginForm";
    }
}
