package de.othr.sw.yetra.dto;

public class ServiceException extends RuntimeException {
    private int code;
    private String description;

    public ServiceException(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
