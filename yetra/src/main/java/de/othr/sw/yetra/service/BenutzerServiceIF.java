package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Angestellter;
import de.othr.sw.yetra.entity.Handelspartner;

public interface BenutzerServiceIF {
    Angestellter angestelltenAnlegen(Angestellter angestellter);
    Angestellter angestelltenAbfragen(long nr);
    Iterable<Angestellter> angestellteAbfragen();

    Handelspartner handelspartnerAnlegen(Handelspartner handelspartner);
    Handelspartner handelspartnerAbfragen(long nr);
    Iterable<Handelspartner> handelspartnerAbfragen();
}
