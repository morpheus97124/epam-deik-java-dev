package com.epam.training.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.StringUtils;

@SpringBootApplication(scanBasePackages = {"com.epam.training.ticketservice.core", "com.epam.training.ticketservice.shell","com.epam.training.ticketservice.core.accounts", "com.epam.training.ticketservice.core.dto"})
@EnableConfigurationProperties
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
