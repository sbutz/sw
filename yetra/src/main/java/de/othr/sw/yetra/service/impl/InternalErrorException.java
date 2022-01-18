package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.service.ServiceException;
import org.springframework.http.HttpStatus;

public class InternalErrorException extends ServiceException {

    public InternalErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
