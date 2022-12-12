package com.epam.training.ticketservice.core.accounts;

import com.epam.training.ticketservice.core.dto.AccountDto;
import java.util.Optional;

public interface AccountService {

    Optional<AccountDto> login(String name, String password);

    Optional<AccountDto> loginPriviliged(String name, String password);

    boolean IsAdminOnline();

    void signOutAdmin();
}
