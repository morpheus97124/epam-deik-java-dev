package com.epam.training.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"com.epam.training.ticketservice.core", "com.epam.training.ticketservice.shell"})
@EnableConfigurationProperties
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
