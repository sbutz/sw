package de.othr.sw.yetra.service;

import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;

public interface ShareServiceIF {
    Iterable<Share> getShares();

    Iterable<Share> getShares(Iterable<String> isins) throws ServiceException;

    ShareDetails getShareDetails(String isin, TimePeriod timePeriod) throws ServiceException;

    //TODO: seperates Interface fuer nicht oeffentliche Methoden?
    Share createShare(Share share) throws ServiceException;
}
