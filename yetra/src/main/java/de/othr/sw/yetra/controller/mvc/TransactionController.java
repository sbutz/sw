package de.othr.sw.yetra.controller.mvc;

import de.othr.sw.yetra.service.TransactionServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Controller
@RequestMapping(path = "/transactions")
@Scope(SCOPE_SINGLETON)
public class TransactionController {

    @Autowired
    private TransactionServiceIF transactionService;

    @Autowired
    @Qualifier("pageSize")
    private int pageSize;

    @GetMapping("")
    @PreAuthorize("hasAuthority('TRANSACTIONS_READ')")
    public String getTransactions(Model model,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
        model.addAttribute("transactions", transactionService.getTransactions(PageRequest.of(page, pageSize)));
        return "transactionTable";
    }
}
