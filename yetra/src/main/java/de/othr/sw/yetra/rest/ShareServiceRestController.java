package de.othr.sw.yetra.rest;

import com.google.common.collect.Iterables;
import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/shares")
public class ShareServiceRestController {
    @Autowired
    private ShareServiceIF shareService;

    //TODO: Doku aendern oder Konstrukur entfernen
    @GetMapping("")
    public Iterable<Share> getShares(
            @RequestParam(name= "filter", required = false) Iterable<String> filter
    ) throws ServiceException {
        return shareService.getShares(filter);
    }

    //TODO: Doku aendern oder Konstrukur entfernen
    @GetMapping("/{isin}")
    public ShareDetails wertpapierDetailsAbfragen(
            @PathVariable(name = "isin") String isin,
            @RequestParam(name = "timePeriod", required = false, defaultValue = "DAY") TimePeriod timePeriod
    ) throws ServiceException {
        return shareService.getShareDetails(isin, timePeriod);
    }

    @PostMapping("")
    public Share wertpapierAnlegen(@Valid @RequestBody Share share) throws ServiceException {
        return shareService.createShare(share);
    }
}
