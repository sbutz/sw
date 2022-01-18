package de.othr.sw.yetra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {

    @Value("${yetra.bank.baseUrl}")
    private String bankBaseUrl;

    @Value("${yetra.bank.user}")
    private String bankUser;

    @Value("${yetra.bank.password}")
    private String bankPassword;

    @Bean
    public Duration defaultTimeout() {
        return Duration.ofSeconds(5);
    }

    @Bean
    public WebClient getBankWebClient() {
        return WebClient.builder()
                .baseUrl(bankBaseUrl)
                .defaultHeaders(h -> h.setBasicAuth(bankUser, bankPassword))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
