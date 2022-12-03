package com.epam.training.ticketservice.core.accounts;

import com.epam.training.ticketservice.core.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;
    private AccountDto activeAccount = null;

    @Override
    public Optional<AccountDto> login(final String name, final String password) {
        final Optional<Account> account = accountRepository.findByNameAndPassword(name, password);
        if(account.isEmpty()){
            return Optional.empty();
        }
        else{
            activeAccount = new AccountDto(activeAccount.getName(), account.get().getPermission());
            return Optional.of(activeAccount);
        }
    }
}
