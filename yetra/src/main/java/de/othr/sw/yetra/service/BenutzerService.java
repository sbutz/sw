package de.othr.sw.yetra.service;

import de.othr.sw.yetra.dto.BereitsAngelegtException;
import de.othr.sw.yetra.dto.NichtGefundenException;
import de.othr.sw.yetra.entity.Angestellter;
import de.othr.sw.yetra.entity.Handelspartner;
import de.othr.sw.yetra.repo.AngestelltenRepository;
import de.othr.sw.yetra.repo.BenutzerRepository;
import de.othr.sw.yetra.repo.HandelspartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BenutzerService implements BenutzerServiceIF, UserDetailsService {
    @Autowired
    private BenutzerRepository benutzerRepo;

    @Autowired
    private AngestelltenRepository angestellenRepo;

    @Autowired
    private HandelspartnerRepository handelspartnerRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Angestellter angestelltenAnlegen(Angestellter angestellter) {
        if (benutzerRepo.existsById(angestellter.getNr())
                || benutzerRepo.findBenutzerByNutzername(angestellter.getNutzername()).isPresent())
            throw new BereitsAngelegtException();

        angestellter.setPasswort(passwordEncoder.encode(angestellter.getPassword()));
        return angestellenRepo.save(angestellter);
    }

    @Override
    public Angestellter angestelltenAbfragen(long nr) {
        return angestellenRepo
                .findById(nr)
                .orElseThrow(() -> {
                    throw new NichtGefundenException();
                });
    }

    @Override
    public Iterable<Angestellter> angestellteAbfragen() {
        return angestellenRepo.findAll();
    }

    @Override
    public Handelspartner handelspartnerAnlegen(Handelspartner handelspartner) {
        if (benutzerRepo.existsById(handelspartner.getNr())
                || benutzerRepo.findBenutzerByNutzername(handelspartner.getNutzername()).isPresent())
            throw new BereitsAngelegtException();

        handelspartner.setPasswort(passwordEncoder.encode(handelspartner.getPassword()));
        return handelspartnerRepository.save(handelspartner);
    }

    @Override
    public Handelspartner handelspartnerAbfragen(long nr) {
        return handelspartnerRepository
                .findById(nr)
                .orElseThrow(() -> {
                    throw new NichtGefundenException();
                });
    }

    @Override
    public Iterable<Handelspartner> handelspartnerAbfragen() {
        return handelspartnerRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String nutzername) throws UsernameNotFoundException {
        return benutzerRepo
                .findBenutzerByNutzername(nutzername)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Benutzer " + nutzername + " existiert nicht");
                });
    }
}
