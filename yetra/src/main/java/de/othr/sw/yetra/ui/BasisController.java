package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.entity.Angestellter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BasisController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startseiteAnzeigen() {
        return "startseite";
    }

    //TODO: get parameter ?error
    @RequestMapping(value = "/anmelden", method = RequestMethod.GET)
    public String anmeldeFormularAnzeigen(Model model) {
        //TODO: benutzer oder handeslpartner duerfen nur api benutzen?
        model.addAttribute("benutzer", new Angestellter());
        model.addAttribute("validiert", false);
        return "anmeldeFormular";
    }
}
