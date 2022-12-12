package com.epam.training.ticketservice;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.epam.training.ticketservice.core.accounts")
@EntityScan(basePackages = "com.epam.training.ticketservice.core.accounts")
public class JPAConfig {

}
