package de.othr.sw.yetra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
public class LoggingConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Logger getLogger() {
        return LoggerFactory.getLogger(appName);
    }
}
