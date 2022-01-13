package de.othr.sw.yetra.service;

import com.google.common.collect.Iterables;
import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.repo.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

//TODO: add transactional/scope

@Service
public class ShareService implements ShareServiceIF {
    @Autowired
    ShareRepository shareRepo;

    @Override
    public Iterable<Share> getShares() {
        return shareRepo.findAll();
    }

    @Override
    public Iterable<Share> getShares(Iterable<String> filter) throws ServiceException {
        if (filter == null || Iterables.isEmpty(filter))
            return this.getShares();

        Collection<Share> shares  = new ArrayList<>();
        for (String isin : filter) {
            Share s = shareRepo
                    .findById(isin)
                    .orElseThrow(()-> {
                        throw new ServiceException(404, "Share not found");
                    });
            shares.add(s);
        }
        return shares;
    }

    @Override
    public ShareDetails getShareDetails(String isin, TimePeriod timePeriod) {
        //TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Share createShare(Share share) throws ServiceException {
        if (shareRepo.existsById(share.getIsin()))
            throw new ServiceException(409, "Share already exists");

       return shareRepo.save(share);
    }
}
