package com.epam.training.ticketservice.core.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AccountsInitializer {

    @Autowired
    private final AccountRepository accountRepository;

    @PostConstruct
    public void initAccount(){
        System.out.println("Account initialization begins...");
        accountRepository.save(new Account("admin", "admin", true));
        accountRepository.save(new Account("kisbela19", "lamborghini", false));
        accountRepository.save(new Account("nagymargit50", "cirmicica", false));
        System.out.println("Account initialization begins...");
    }
}
