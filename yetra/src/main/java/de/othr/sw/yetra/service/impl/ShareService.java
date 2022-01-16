package de.othr.sw.yetra.service.impl;

import com.google.common.collect.Iterables;
import de.othr.sw.yetra.dto.*;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.entity.Transaction;
import de.othr.sw.yetra.repository.ShareRepository;
import de.othr.sw.yetra.service.ServiceException;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

//TODO: add transactional

@Service
@Scope(SCOPE_SINGLETON)
public class ShareService implements ShareServiceIF {
    @Autowired
    private ShareRepository shareRepo;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Share createShare(Share share) throws ServiceException {
        if (shareRepo.existsById(share.getIsin()))
            throw new ServiceException(409, "Share already exists");

        return shareRepo.save(share);
    }

    @Override
    public ShareDetailsDTO getShareDetails(String isin, TimePeriodDTO timePeriod) throws ServiceException {
        Share share = this.getShare(isin);
        Collection<MarketValueDTO> marketValues = new ArrayList<>();
        int values;
        TemporalUnit unit;
        switch (timePeriod) {
            case DAY -> {
                values = 24;
                unit = ChronoUnit.HOURS;
            }
            case WEEK -> {
                values = 14;
                unit = ChronoUnit.HALF_DAYS;
            }
            case MONTH -> {
                values = 30;
                unit = ChronoUnit.DAYS;
            }
            case YEAR -> {
                values = 12;
                unit = ChronoUnit.MONTHS;
            }
            default -> throw new IllegalStateException("Unexpected value: " + timePeriod);
        }
        /*
         * This could have been implemented easier using SQL Rank, but JPQL does not provide analytic functions.
         * Native SQL is forbidden by the requirements. Emulating SQL Rank with subqueries is ugly and not performant.
         */
        for (int i = values; i > 0; i--) {
            try {
                LocalDateTime start = LocalDateTime.now().minus(i, unit);
                LocalDateTime end = LocalDateTime.now().minus(i-1, unit);
                Transaction t = transactionService.getLastTransaction(share, start, end);
                marketValues.add(new MarketValueDTO(t.getTimestamp(), t.getUnitPrice()));
            } catch (ServiceException ignored) { }
        }

        return new ShareDetailsDTO(this.getShare(isin), marketValues);
    }

    @Override
    public Share getShare(String isin) throws ServiceException {
        return shareRepo
                .findById(isin)
                .orElseThrow(()-> {
                    throw new ServiceException(404, "Share not found");
                });
    }


    @Override
    public Page<Share> getShares(Pageable pageable) {
        return shareRepo.findAll(pageable);
    }

    @Override
    public Iterable<Share> getShares(Iterable<String> filter) throws ServiceException {
        if (filter == null || Iterables.isEmpty(filter))
            throw new IllegalArgumentException("filter must not be null or empty");

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
}
