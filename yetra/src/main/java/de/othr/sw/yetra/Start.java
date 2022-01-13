package de.othr.sw.yetra;

import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.repo.ShareRepository;

import de.othr.sw.yetra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Start implements CommandLineRunner {

    @Autowired
    ShareRepository shareRepo;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) {
        //TODO: Mit ordentlichem Aktienimport ersetzen
        shareRepo.save(new Share("DE0005190003", "BMW AG", 58.55f));
        shareRepo.save(new Share("DE0007664039", "Volkswagen Group", 180.0f));
        shareRepo.save(new Share("US88160R1014", "Telsa, Inc.", 832.90f));

        //TODO: testnutzer in readme vermerken
        //TODO: min passwort length?
        userService.createEmployee(new Employee("admin", "123"));
    }
}
