package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.service.BankTransferServiceIF;
import eBank.DTO.UeberweisungDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BankTransferService implements BankTransferServiceIF {

    @Autowired
    private Logger logger;

    @Autowired
    private WebClient webClient;

    @Autowired
    private Duration timeout;

    @Override
    public void transfer(UeberweisungDTO ueberweisung) {
        if (ueberweisung.getIbanEmpfaenger().equals(ueberweisung.getIbanSender()))
            return;

        ResponseEntity<Void> response;
        try {
            response = webClient
                    .post()
                    .uri(uriBuilder -> uriBuilder.path("/restapi/ueberweisung").build())
                    .body(Mono.just(ueberweisung), UeberweisungDTO.class)
                    .retrieve()
                    .toBodilessEntity()
                    .block(timeout);
        } catch (WebClientRequestException e) {
            throw  new ServiceUnavailableException("Bank transfer service not available.");
        } catch (Exception e) {
            logger.error("Error during bank transfer");
            logger.error(e.getMessage());
            throw new InternalErrorException("Error during bank transfer.");
        }

        if (response == null) {
            throw new InternalErrorException("Bank transfer failed with empty response.");
        } else if (!response.getStatusCode().is2xxSuccessful()) {
            logger.error("Bank transfer failed with status " + response.getStatusCode().value());
            throw new InternalErrorException("Bank transfer failed.");
        }
    }
}
