package de.othr.sw.yetra.controller.mvc;

import de.othr.sw.yetra.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(value = "de.othr.sw.yetra.controller.mvc")
public class ErrorController {

    @ExceptionHandler(ServiceException.class)
    public String handleServiceError(ServiceException e,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model)
    {
        response.setStatus(e.getStatus().value());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("lastPage", request.getRequestURI());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleServiceError(Exception e,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model)
    {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("message", "This should not have happened.");
        model.addAttribute("lastPage", request.getRequestURI());
        return "error";
    }
}
