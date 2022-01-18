package de.othr.sw.yetra.service;

import eBank.DTO.UeberweisungDTO;

public interface BankTransferServiceIF {

    boolean transfer(UeberweisungDTO ueberweisung) throws ServiceException;
}
