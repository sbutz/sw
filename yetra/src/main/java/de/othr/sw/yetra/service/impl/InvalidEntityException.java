package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.service.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidEntityException extends ServiceException {

    public InvalidEntityException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
