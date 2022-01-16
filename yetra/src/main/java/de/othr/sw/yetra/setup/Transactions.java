package de.othr.sw.yetra.setup;

import com.google.common.collect.Iterables;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repository.OrderRepository;
import de.othr.sw.yetra.repository.ShareRepository;
import de.othr.sw.yetra.repository.TransactionRepository;
import de.othr.sw.yetra.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Component
@DependsOn(Users.component)
public class Transactions {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ShareRepository shareRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Value("classpath:data/share_values.csv")
    private Resource data;

    private User importUser;

    private HashMap<String, Share> shares = new HashMap<>();

    private DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @PostConstruct
    @Transactional
    public void createTransactions() {
        if (!Iterables.isEmpty(transactionRepo.findAll())) {
            //TODO: logger: import has already run
            return;
        }
        importUser = userRepo.findUserByUsername("import").get();

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setDelimiter(',')
                .setQuote('"')
                .setRecordSeparator("\r\n")
                .setIgnoreEmptyLines(true)
                .setHeader().setSkipHeaderRecord(true)
                .build();

        try (CSVParser parser = csvFormat.parse(new InputStreamReader(data.getInputStream()))) {
            int i = 0;
            //TODO: batch saving
            for (CSVRecord record : parser) {
                createTransactionFromLine(record);
                System.out.println(i++);
            }
        } catch (IOException e) {
            //TODO: logger import failed
            e.printStackTrace();
        }
    }

    private void createTransactionFromLine(CSVRecord record) {
        String isin = record.get("isin");
        String name = record.get("name");
        double price = Double.parseDouble(record.get("value"));
        LocalDateTime timestamp = LocalDateTime.parse(record.get("timestamp"), timestampFormat);
        int quantity = 1;
        BankAccount account = new BankAccount("DE0123456789");

        Share s = getOrCreateShare(isin, name);
        s.setCurrentPrice(price);

        Order buy = new Order();
        buy.setType(OrderType.BUY);
        buy.setShare(s);
        buy.setQuantity(quantity);
        buy.setUnitPrice(price);
        buy.setStatus(OrderStatus.CLOSED);
        buy.setClient(importUser);
        buy.setTimestamp(timestamp);
        buy.setBankAccount(account);
        buy = orderRepo.save(buy);

        Order sell = new Order();
        sell.setType(OrderType.SELL);
        sell.setShare(s);
        sell.setQuantity(quantity);
        sell.setUnitPrice(price);
        sell.setStatus(OrderStatus.CLOSED);
        sell.setClient(importUser);
        sell.setTimestamp(timestamp);
        sell.setBankAccount(account);
        sell = orderRepo.save(sell);

        Transaction t = new Transaction();
        t.setTimestamp(timestamp);
        t.setUnitPrice(price);
        t.setShare(s);
        t.setBuyOrder(buy);
        t.setSellOrder(sell);
        transactionRepo.save(t);
    }

    private Share getOrCreateShare(String isin, String name) {
        if (shares.containsKey(isin))
            return shares.get(isin);
        else
            return shareRepo.save(new Share(isin, name, 0));
    }
}
