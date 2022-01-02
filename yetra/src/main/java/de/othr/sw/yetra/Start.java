package de.othr.sw.yetra;

import de.othr.sw.yetra.entity.Angestellter;
import de.othr.sw.yetra.entity.Wertpapier;
import de.othr.sw.yetra.repo.WertpapierRepository;

import de.othr.sw.yetra.service.BenutzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Start implements CommandLineRunner {

    @Autowired
    WertpapierRepository repo;

    @Autowired
    BenutzerService benutzerService;

    @Override
    public void run(String... args) {
        //TODO: Mit ordentlichem Aktienimport ersetzen
        repo.save(new Wertpapier("DE0005190003", "BMW AG", 58.55f));
        repo.save(new Wertpapier("DE0007664039", "Volkswagen Group", 180.0f));
        repo.save(new Wertpapier("US88160R1014", "Telsa, Inc.", 832.90f));

        //TODO: testnutzer in readme vermerken
        //TODO: min passwort length?
        benutzerService.angestelltenAnlegen(new Angestellter("admin", "123"));
    }
}
