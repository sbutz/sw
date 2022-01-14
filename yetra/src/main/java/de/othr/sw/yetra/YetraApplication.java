package de.othr.sw.yetra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YetraApplication {

    public static void main(String[] args) {
        SpringApplication.run(YetraApplication.class, args);
    }

}
