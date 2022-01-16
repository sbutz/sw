package de.othr.sw.yetra.controller.rest;

import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.service.ServiceException;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@RestController
@RequestMapping(path = "/api/shares")
@Scope(SCOPE_SINGLETON)
public class ShareServiceRestController {

    @Autowired
    private ShareServiceIF shareService;

    @GetMapping("")
    public Iterable<Share> getShares(
            @RequestParam(name= "filter", required = false) Iterable<String> filter
    ) throws ServiceException {
        return shareService.getShares(filter);
    }

    @GetMapping("/{isin}")
    public ShareDetailsDTO getShareDetails(
            @PathVariable(name = "isin") String isin,
            @RequestParam(name = "timePeriod", required = false, defaultValue = "DAY") TimePeriodDTO timePeriod
    ) throws ServiceException {
        return shareService.getShareDetails(isin, timePeriod);
    }
}
