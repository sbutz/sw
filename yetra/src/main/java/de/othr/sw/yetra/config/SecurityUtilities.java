package de.othr.sw.yetra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class SecurityUtilities {

    //TODO: inject from extern (siehe Folien)
    private String salt = "XXX";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        //TODO: use more rounds
        return new BCryptPasswordEncoder(10, new SecureRandom(salt.getBytes()));
    }
}
