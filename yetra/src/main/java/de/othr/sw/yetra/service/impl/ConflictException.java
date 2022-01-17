package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.service.ServiceException;
import org.springframework.http.HttpStatus;

public class ConflictException extends ServiceException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
