package de.othr.sw.yetra.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: @ControllerAdvice fuer response codes statt hier?
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NichtGefundenException extends RuntimeException {
    public NichtGefundenException(String beschreibung) {
        super(beschreibung);
    }
}
