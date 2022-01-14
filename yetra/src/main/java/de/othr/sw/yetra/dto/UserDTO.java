package de.othr.sw.yetra.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UserDTO implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public UserDTO() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
