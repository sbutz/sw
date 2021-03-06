package de.othr.sw.yetra.setup;

import com.google.common.collect.Iterables;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repository.OrderRepository;
import de.othr.sw.yetra.repository.ShareRepository;
import de.othr.sw.yetra.repository.TransactionRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class Transactions {

    @Autowired
    private Logger logger;

    @Autowired
    private ShareRepository shareRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Value("classpath:data/share_values.csv")
    private Resource data;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Autowired
    @Qualifier("import")
    private User importUser;

    private HashMap<String, Share> shares = new HashMap<>();

    private DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @PostConstruct
    /*
     * @Transactional does not work on @PostConstruct, because the interceptor proxy starts working after
     * the bean is fully initialized.
     * As a result some manual calls to save() are necessary.
     */
    public void createTransactions() {
        if (!Iterables.isEmpty(transactionRepo.findAll(PageRequest.of(0,1)))) {
            logger.info("Transactions repo not empty. Skipping import...");
            return;
        }
        logger.info("Creating Transactions...");

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setDelimiter(',')
                .setQuote('"')
                .setRecordSeparator("\r\n")
                .setIgnoreEmptyLines(true)
                .setHeader().setSkipHeaderRecord(true)
                .build();

        try (CSVParser parser = csvFormat.parse(new InputStreamReader(data.getInputStream()))) {
            Collection<CSVRecord> records = new ArrayList<>();
            int i =0;
            for (CSVRecord record : parser) {
                records.add(record);
                if (records.size() == batchSize) {
                    createTransactions(records);
                    i += batchSize;
                    logger.debug("Imported " + batchSize + " transactions so far");
                    records = new ArrayList<>();
                }
            }
            if (!records.isEmpty())
                createTransactions(records);
        } catch (IOException e) {
            logger.error("Failed to read " + data.getDescription());
            e.printStackTrace();
        }
    }

    private void createTransactions(Iterable<CSVRecord> records) {
        Collection<Order> buyOrders = new ArrayList<>();
        Collection<Order> sellOrders = new ArrayList<>();
        BankAccount account = new BankAccount("DE0123456789");

        for (CSVRecord record : records) {
            String isin = record.get("isin");
            String name = record.get("name");
            double price = Double.parseDouble(record.get("value"));
            int quantity = 1;
            LocalDateTime timestamp = LocalDateTime.parse(record.get("timestamp"), timestampFormat);

            Share s = getOrCreateShare(isin, name);
            s.setCurrentPrice(price);
            shareRepo.save(s);

            Order buy = new Order();
            buy.setType(OrderType.BUY);
            buy.setShare(s);
            buy.setQuantity(quantity);
            buy.setUnitPrice(price);
            buy.setStatus(OrderStatus.CLOSED);
            buy.setClient(importUser);
            buy.setTimestamp(timestamp);
            buyOrders.add(buy);

            Order sell = new Order();
            sell.setType(OrderType.SELL);
            sell.setShare(s);
            sell.setQuantity(quantity);
            sell.setUnitPrice(price);
            sell.setStatus(OrderStatus.CLOSED);
            sell.setClient(importUser);
            sell.setTimestamp(timestamp);
            sellOrders.add(sell);
        }

        Iterator<Order> buyIt = orderRepo.saveAll(buyOrders).iterator();
        Iterator<Order> sellIt = orderRepo.saveAll(sellOrders).iterator();
        Collection<Transaction> transactions = new ArrayList<>();

        while (buyIt.hasNext() && sellIt.hasNext()) {
            Order buy = buyIt.next();
            Order sell = sellIt.next();

            Transaction t = new Transaction();
            t.setTimestamp(buy.getTimestamp());
            t.setUnitPrice(buy.getUnitPrice());
            t.setShare(buy.getShare());
            t.setBuyOrder(buy);
            t.setSellOrder(sell);
            transactions.add(t);
        }
        transactionRepo.saveAll(transactions);
    }

    private Share getOrCreateShare(String isin, String name) {
        if (shares.containsKey(isin))
            return shares.get(isin);
        else
            return shareRepo.save(new Share(isin, name, 0));
    }
}
