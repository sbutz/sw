package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.service.ServiceException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
