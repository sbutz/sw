package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.entity.Wertpapier;
import de.othr.sw.yetra.service.WertpapierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class WertpapierController {

    @Autowired
    WertpapierService wertpapierService;

    //TODO: pagination
    @RequestMapping(value = "/wertpapiere", method = RequestMethod.GET)
    public String wertpapiereAnzeigen(Model model) {
        model.addAttribute("wertpapiere", wertpapierService.wertpapiereAbfragen());
        return "wertpapiere";
    }

    @RequestMapping(value = "/wertpapiere/anlegen", method = RequestMethod.GET)
    public String wertpapierFormularAnzeigen(Model model) {
        model.addAttribute("wertpapier", new Wertpapier());
        model.addAttribute("validiert", false);
        return "wertpapierFormular";
    }

    @RequestMapping(value = "/wertpapiere/anlegen", method = RequestMethod.POST)
    public String wertpapierErstellen(
            Model model,
            @Valid @ModelAttribute("wertpapier") Wertpapier wertpapier,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("validiert", true);
            return "wertpapierFormular";
        } else {
            wertpapierService.wertpapierErstellen(wertpapier);
            return "redirect:/wertpapiere";
        }
    }
}
