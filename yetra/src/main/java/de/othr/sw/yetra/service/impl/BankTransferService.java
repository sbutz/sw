package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.service.BankTransferServiceIF;
import eBank.DTO.UeberweisungDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.time.Duration;

@Service
public class BankTransferService implements BankTransferServiceIF {

    @Autowired
    private WebClient webClient;

    @Autowired
    private Duration timeout;

    @Override
    public boolean transfer(UeberweisungDTO ueberweisung) {
        //TODO: test when server available (is http status set in error case?)
        ResponseEntity<Void> response = webClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/ueberweisung").build())
                .body(BodyInserters.fromValue(ueberweisung))
                .retrieve()
                .toBodilessEntity()
                .onErrorMap(WebClientRequestException.class,
                        e -> new ServiceUnavailableException("Bank Transfer Service not available."))
                .block(timeout);

        if (response == null)
            throw new ServiceUnavailableException("Bank Transfer Service not available.");
        else
            return response.getStatusCode().is2xxSuccessful();
    }
}
