package de.othr.sw.yetra.service;

import de.othr.sw.yetra.dto.BereitsAngelegtException;
import de.othr.sw.yetra.dto.NichtGefundenException;
import de.othr.sw.yetra.dto.WertpapierDetails;
import de.othr.sw.yetra.dto.Zeitspanne;
import de.othr.sw.yetra.entity.Wertpapier;

import java.util.Collection;

public interface WertpapierServiceIF {
    //TODO: use Iterable
    Collection<Wertpapier> wertpapiereAbfragen();

    //TODO: use Iterable twice
    Collection<Wertpapier> wertpapiereAbfragen(Collection<Wertpapier> wertpapiere) throws NichtGefundenException;

    WertpapierDetails wertpapierDetailsAbfragen(Wertpapier wertpapier, Zeitspanne zeitspanne) throws NichtGefundenException;

    //TODO: seperates Interface fuer nicht oeffentliche Methoden?
    Wertpapier wertpapierErstellen(Wertpapier wertpapier) throws BereitsAngelegtException;
}
