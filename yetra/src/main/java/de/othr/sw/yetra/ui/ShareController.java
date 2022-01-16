package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ShareController {

    @Autowired
    ShareService shareService;

    @GetMapping(value = "/shares")
    public String getShares(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
        model.addAttribute("shares", shareService.getShares(PageRequest.of(page, 20)));
        return "shareList";
    }

    @GetMapping(value = "/shares/create")
    public String getShareForm(Model model) {
        model.addAttribute("share", new Share());
        model.addAttribute("validated", false);
        return "shareForm";
    }

    @PostMapping(value = "/shares/create")
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
