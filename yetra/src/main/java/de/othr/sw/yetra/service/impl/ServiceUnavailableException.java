package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.service.ServiceException;
import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends ServiceException {

    public ServiceUnavailableException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }
}
