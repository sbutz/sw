package de.othr.sw.yetra.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
public class ThymeleafConfiguration {
    @Bean
    @Scope(SCOPE_SINGLETON)
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
