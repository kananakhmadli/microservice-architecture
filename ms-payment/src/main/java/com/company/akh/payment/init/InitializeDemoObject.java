package com.company.akh.payment.init;

import com.company.akh.payment.entity.Account;
import com.company.akh.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InitializeDemoObject {

    private final AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        var account1 = new Account();
        account1.setUserId(19L);
        account1.setAmount(BigDecimal.valueOf(700));
        account1.setAccountNumber("38820840000035503134");

        var account2 = new Account();
        account2.setUserId(20L);
        account2.setAmount(BigDecimal.valueOf(3000));
        account2.setAccountNumber("38820840000035503125");

        var account3 = new Account();
        account3.setUserId(21L);
        account3.setAmount(BigDecimal.valueOf(100));
        account3.setAccountNumber("38820840000035503116");

        accountRepository.save(account1);
        accountRepository.save(account2);
        accountRepository.save(account3);
    }

}