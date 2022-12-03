package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.accounts.AccountServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@Configuration
@EnableJpaRepositories(basePackages = "com.epam.training.ticketservice.core.accounts")
@EntityScan(basePackages = "com.epam.training.ticketservice.core.accounts")
public class JPAConfig {
    /*@Bean
    public AccountService accountService(){
        return new AccountServiceImpl();
    }*/
}
