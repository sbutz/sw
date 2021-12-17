package de.othr.sw.yetra.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartseiteController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startseiteAnzeigen() {
        return "startseite";
    }
}
