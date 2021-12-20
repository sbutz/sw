package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.entity.Angestellter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BasisController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startseiteAnzeigen() {
        return "startseite";
    }

    @RequestMapping(value = "/anmelden", method = RequestMethod.GET)
    public String anmeldeFormularAnzeigen(Model model, @RequestParam(name = "fehler", defaultValue = "false") boolean fehler) {
        //TODO: benutzer oder handeslpartner duerfen nur api benutzen?
        model.addAttribute("benutzer", new Angestellter());
        model.addAttribute("validiert", fehler);
        return "anmeldeFormular";
    }
}
