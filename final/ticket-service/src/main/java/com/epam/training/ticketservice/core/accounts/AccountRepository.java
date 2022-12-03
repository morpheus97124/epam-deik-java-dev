package com.epam.training.ticketservice.core.accounts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByNameAndPassword(String name, String password);
}