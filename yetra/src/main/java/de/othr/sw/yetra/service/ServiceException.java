package de.othr.sw.yetra.service;

//TODO: Checked or Unchecked (unchecked = subclass of RuntimeException)
//TODO: use SErviceExecpetion from hibernate/spring
public class ServiceException extends RuntimeException {

    private int code;
    private String description;

    public ServiceException(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
