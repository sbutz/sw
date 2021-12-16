package de.othr.sw.yetra.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: @ControllerAdvice für response codes statt hier?
//TODO: Beschreibung fuer eigene Exceptions?
//TODO: in doku ändern
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NichtGefundenException extends RuntimeException {
}
