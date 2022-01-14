package de.othr.sw.yetra.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TradingPartnerDTO extends UserDTO implements Serializable {
    @NotBlank
    @Size(min = 4, max = 34)
    private String iban;

    public TradingPartnerDTO() {

    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
