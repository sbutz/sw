package de.othr.sw.yetra.rest;

import de.othr.sw.yetra.dto.BereitsAngelegtException;
import de.othr.sw.yetra.dto.NichtGefundenException;
import de.othr.sw.yetra.dto.WertpapierDetails;
import de.othr.sw.yetra.dto.Zeitspanne;
import de.othr.sw.yetra.entity.Wertpapier;
import de.othr.sw.yetra.service.WertpapierServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/wertpapiere")
public class WertpapierServiceRestController {
    //TODO: Klasse oder Interface injizieren?
    @Autowired
    private WertpapierServiceIF wertpapierService;

    //TODO: Doku aendern oder Konstrukur entfernen
    @GetMapping("")
    public Collection<Wertpapier> wertpapiereAbfragen(
            @RequestParam(name= "filter", required = false) Collection<String> filter
    ) throws NichtGefundenException {
        if (filter == null || filter.isEmpty()) {
            return wertpapierService.wertpapiereAbfragen();
        } else {
            Collection<Wertpapier> wertpapiere = filter.stream().map(Wertpapier::new).collect(Collectors.toList());
            return wertpapierService.wertpapiereAbfragen(wertpapiere);
        }
    }

    //TODO: Doku aendern oder Konstrukur entfernen
    @GetMapping("/{isin}")
    public WertpapierDetails wertpapierDetailsAbfragen(
            @PathVariable(name = "isin") String isin,
            @RequestParam(name = "filter", required = false, defaultValue = "TAG") Zeitspanne zeitspanne
    ) throws NichtGefundenException {
        return wertpapierService.wertpapierDetailsAbfragen(new Wertpapier(isin), zeitspanne);
    }

    @PostMapping("")
    public Wertpapier wertpapierAnlegen(@RequestBody Wertpapier wertpapier) throws BereitsAngelegtException {
        return wertpapierService.wertpapierAnlegen(wertpapier);
    }
}
