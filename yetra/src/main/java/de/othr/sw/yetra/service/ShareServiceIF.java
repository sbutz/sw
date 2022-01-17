package de.othr.sw.yetra.service;

import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//TODO: gleiche Reihenfolge der Methoden bei allen services
//TODO: seperates Interface fuer nicht oeffentliche Methoden?
public interface ShareServiceIF {

    Share createShare(Share share) throws ServiceException;

    Share getShare(String isin) throws ServiceException;

    ShareDetailsDTO getShareDetails(String isin, TimePeriodDTO timePeriod) throws ServiceException;

    Page<Share> getShares(Pageable pageable);

    Iterable<Share> getShares(Iterable<String> isins) throws ServiceException;
}
