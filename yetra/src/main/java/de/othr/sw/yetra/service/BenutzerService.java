package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Benutzer;
import de.othr.sw.yetra.repo.BenutzerRepository;
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
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Benutzer benutzerAnlegen(Benutzer benutzer) {
        benutzer.setPasswort(passwordEncoder.encode(benutzer.getPassword()));
        return benutzerRepo.save(benutzer);
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
