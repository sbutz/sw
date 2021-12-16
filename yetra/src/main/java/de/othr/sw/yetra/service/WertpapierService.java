package de.othr.sw.yetra.service;

import de.othr.sw.yetra.dto.BereitsAngelegtException;
import de.othr.sw.yetra.dto.NichtGefundenException;
import de.othr.sw.yetra.dto.WertpapierDetails;
import de.othr.sw.yetra.dto.Zeitspanne;
import de.othr.sw.yetra.entity.Wertpapier;
import de.othr.sw.yetra.repo.WertpapierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

//TODO: add transactional/scope

@Service
public class WertpapierService implements WertpapierServiceIF {
    @Autowired
    WertpapierRepository wertpapierRepo;

    @Override
    public Collection<Wertpapier> wertpapiereAbfragen() {
        Collection<Wertpapier> wertpapiere = new ArrayList<>();
        wertpapierRepo.findAll().forEach(wertpapiere::add);
        return wertpapiere;
    }

    @Override
    public Collection<Wertpapier> wertpapiereAbfragen(Collection<Wertpapier> filter) throws NichtGefundenException {
        Collection<Wertpapier> wertpapiere  = new ArrayList<>();
        for (Wertpapier f : filter) {
            Wertpapier w = wertpapierRepo
                    .findById(f.getIsin())
                    .orElseThrow(NichtGefundenException::new);
            wertpapiere.add(w);
        }
        return wertpapiere;
    }

    @Override
    public WertpapierDetails wertpapierDetailsAbfragen(Wertpapier wertpapier, Zeitspanne zeitspanne) {
        //TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Wertpapier wertpapierErstellen(Wertpapier wertpapier) throws BereitsAngelegtException {
        if (wertpapierRepo.existsById(wertpapier.getIsin()))
            throw new BereitsAngelegtException();

       return wertpapierRepo.save(wertpapier);
    }
}
