package de.othr.sw.yetra.controller.mvc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Controller
@Scope(SCOPE_SINGLETON)
public class BasisController {
    @GetMapping(value = "/")
    public String getHomepage() {
        return "homepage";
    }

    @GetMapping(value = "/login")
    public String getLoginForm(
            Model model,
            @RequestParam(name = "error", defaultValue = "false") boolean error
    ) {
        model.addAttribute("validated", error);
        return "loginForm";
    }
}
