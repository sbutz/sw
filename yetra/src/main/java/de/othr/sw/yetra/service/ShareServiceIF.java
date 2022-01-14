package de.othr.sw.yetra.service;

import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;

//TODO: gleiche Reihenfolge der Methoden bei allen services
public interface ShareServiceIF {
    //TODO: seperates Interface fuer nicht oeffentliche Methoden?
    Share createShare(Share share) throws ServiceException;

    Share getShare(String isin) throws ServiceException;

    ShareDetailsDTO getShareDetails(String isin, TimePeriodDTO timePeriod) throws ServiceException;

    Iterable<Share> getShares();

    Iterable<Share> getShares(Iterable<String> isins) throws ServiceException;
}
