package de.othr.sw.yetra.dto.util;

import de.othr.sw.yetra.dto.TradingPartnerDTO;
import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.TradingPartner;
import org.springframework.stereotype.Component;

@Component
public class TradingPartnerDTOMapper implements DTOMapper<TradingPartner, TradingPartnerDTO> {

    @Override
    public TradingPartnerDTO toDTO(TradingPartner partner) {
        TradingPartnerDTO dto = new TradingPartnerDTO();
        dto.setUsername(partner.getUsername());
        //dto.setPassword(partner.getPassword());
        dto.setIban(partner.getBillingBankAccount().getIban());
        dto.setNotifyChannelName(partner.getNotifyChannelName());
        return dto;
    }

    @Override
    public TradingPartner fromDTO(TradingPartnerDTO dto) {
        TradingPartner partner = new TradingPartner();
        partner.setUsername(dto.getUsername());
        partner.setPassword(dto.getPassword());
        partner.setBillingBankAccount(new BankAccount(dto.getIban()));
        partner.setNotifyChannelName(dto.getNotifyChannelName());
        return partner;
    }
}
