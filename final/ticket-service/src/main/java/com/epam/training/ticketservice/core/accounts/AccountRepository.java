package com.epam.training.ticketservice.core.accounts;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByNameAndPassword(String name, String password);

    Optional<Account> findByName(String name);
}