package de.othr.sw.yetra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
public class SecurityUtilities {

    @Value("${user-password-rounds}")
    private int rounds;

    @Value("${user-password-salt}")
    private String salt;

    @Bean
    @Scope(SCOPE_SINGLETON)
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(rounds, new SecureRandom(salt.getBytes()));
    }
}
