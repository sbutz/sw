package de.othr.sw.yetra.service;

import eBank.DTO.UeberweisungDTO;

public interface BankTransferServiceIF {

    void transfer(UeberweisungDTO ueberweisung) throws ServiceException;
}
