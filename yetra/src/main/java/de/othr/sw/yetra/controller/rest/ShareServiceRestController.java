package de.othr.sw.yetra.controller.rest;

import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@RestController
@RequestMapping(path = "/api/shares")
@Scope(SCOPE_SINGLETON)
public class ShareServiceRestController {

    @Autowired
    private ShareServiceIF shareService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('SHARES_READ')")
    public Iterable<Share> getShares(@RequestParam(name= "filter", required = false) Optional<String[]> filter,
                                     @RequestParam(name= "name", required = false) Optional<String> name
    ) {
        if (filter.isPresent())
            return shareService.getShares(Arrays.asList(filter.get()));
        if (name.isPresent())
            return shareService.getSharesNameContains(name.get());
        else
            return shareService.getShares(Pageable.unpaged());
    }

    @GetMapping("/{isin}")
    @PreAuthorize("hasAuthority('SHARES_READ')")
    public ShareDetailsDTO getShareDetails(@PathVariable(name = "isin") String isin,
                                           @RequestParam(name = "timePeriod", required = false, defaultValue = "DAY") TimePeriodDTO timePeriod
    ) {
        return shareService.getShareDetails(isin, timePeriod);
    }
}
