package com.epam.training.ticketservice.core.accounts;

import com.epam.training.ticketservice.core.dto.AccountDto;
import org.springframework.data.util.Pair;

import java.util.Optional;

public interface AccountService {

    Optional<AccountDto> login(String name, String password);

    Optional<AccountDto> loginPriviliged(String name, String password);

    boolean IsAdminOnline();

    void signOutAdmin();

    //Optional<AccountDto> returnAccountDto(String name);
}
