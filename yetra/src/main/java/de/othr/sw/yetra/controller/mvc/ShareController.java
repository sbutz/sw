package de.othr.sw.yetra.controller.mvc;

import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.service.impl.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Controller
@RequestMapping(path = "/shares")
@Scope(SCOPE_SINGLETON)
public class ShareController {

    @Autowired
    private ShareService shareService;

    @Autowired
    @Qualifier("pageSize")
    private int pageSize;

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('SHARES_READ')")
    public String getShares(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
        model.addAttribute("shares", shareService.getShares(PageRequest.of(page, pageSize)));
        return "shareTable";
    }

    @GetMapping(value = "/create")
    @PreAuthorize("hasAuthority('SHARES_WRITE')")
    public String getShareForm(Model model) {
        model.addAttribute("share", new Share());
        model.addAttribute("validated", false);
        return "shareForm";
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('SHARES_WRITE')")
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
