package de.othr.sw.yetra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfiguration {

    @Bean("pageSize")
    public int getPageSize() {
        return 20;
    }
}
