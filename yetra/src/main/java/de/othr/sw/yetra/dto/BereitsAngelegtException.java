package de.othr.sw.yetra.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
//TODO: besserer Name?
public class BereitsAngelegtException extends RuntimeException {
}
