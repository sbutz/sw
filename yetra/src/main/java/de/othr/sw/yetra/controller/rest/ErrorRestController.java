package de.othr.sw.yetra.controller.rest;

import de.othr.sw.yetra.dto.ApiErrorDTO;
import de.othr.sw.yetra.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(value = "de.othr.sw.yetra.controller.rest")
public class ErrorRestController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceError(ServiceException e, HttpServletResponse response) {
        response.setStatus(e.getStatus().value());
        return new ResponseEntity<>(
                new ApiErrorDTO(e.getStatus().toString(), e.getMessage()),
                e.getStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleServiceError(Exception e, HttpServletResponse response) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        response.setStatus(status.value());
        return new ResponseEntity<>(
                new ApiErrorDTO(status.toString(), "This should not have happend."),
                status
        );
    }
}
