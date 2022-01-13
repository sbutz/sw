package de.othr.sw.yetra;

import de.othr.sw.yetra.entity.Share;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=YetraApplication.class)
public class RestApiTests {

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
        WebTestClient webClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        webClient
                .get().uri("/api/shares").exchange()
                .expectStatus().isUnauthorized();

        webClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .defaultHeaders(header -> header.setBasicAuth("admin", "123"))
                .build();

        webClient
                .get().uri("/api/shares")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Share.class).hasSize(3);
    }

}
