package de.othr.sw.yetra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
public class LoggingConfiguration {

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Logger getLogger() {
        return LoggerFactory.getLogger("yetra");
    }
}
