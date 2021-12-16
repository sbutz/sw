package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.service.WertpapierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WertpapierController {

    @Autowired
    WertpapierService wertpapierService;

    //TODO: pagination
    @RequestMapping(value = "/wertpapiere")
    public String wertpapiereAnzeigen(Model model) {
        model.addAttribute("wertpapiere", wertpapierService.wertpapiereAbfragen());
       return "wertpapiere";
    }
}
