package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BasisController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHomepage() {
        return "homepage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String anmeldeFormularAnzeigen(
            Model model,
            @RequestParam(name = "error", defaultValue = "false") boolean error
    ) {
        //TODO: benutzer oder handeslpartner duerfen nur api benutzen?
        model.addAttribute("user", new Employee());
        model.addAttribute("validated", error);
        return "loginForm";
    }
}
