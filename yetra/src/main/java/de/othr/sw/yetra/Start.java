package de.othr.sw.yetra;

import de.othr.sw.yetra.entity.Wertpapier;
import de.othr.sw.yetra.repo.WertpapierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: Mit ordentlichem Aktienimport ersetzen

@SpringBootApplication
public class Start implements CommandLineRunner {

    @Autowired
    WertpapierRepository repo;

    @Override
    public void run(String... args) {
        repo.save(new Wertpapier("DE0005190003", "BMW AG"));
        repo.save(new Wertpapier("DE0007664039", "Volkswagen Group"));
        repo.save(new Wertpapier("US88160R1014", "Telsa, Inc."));
    }
}
