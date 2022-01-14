package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class ShareController {

    @Autowired
    ShareService shareService;

    //TODO: pagination
    @RequestMapping(value = "/shares", method = RequestMethod.GET)
    public String getShares(Model model) {
        model.addAttribute("shares", shareService.getShares());
        return "shareList";
    }

    @RequestMapping(value = "/shares/create", method = RequestMethod.GET)
    public String getShareForm(Model model) {
        model.addAttribute("share", new Share());
        model.addAttribute("validated", false);
        return "shareForm";
    }

    @RequestMapping(value = "/shares/create", method = RequestMethod.POST)
    public String createShare(
            Model model,
            @Valid @ModelAttribute("wertpapier") Share share,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("share", share);
            model.addAttribute("validated", true);
            return "shareForm";
        } else {
            shareService.createShare(share);
            return "redirect:/shares";
        }
    }
}
