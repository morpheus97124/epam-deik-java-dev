package com.epam.training.ticketservice.core.accounts;

import com.epam.training.ticketservice.core.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
            activeAccount = new AccountDto(account.get().getName(), account.get().getPermission(), account.get().isLoggedIn());
            if(activeAccount.getPermission().equals(Permission.USER)){
                account.get().setLoggedIn(true);
                accountRepository.save(account.get());
                return Optional.of(activeAccount);
            }
            else{
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<AccountDto> loginPriviliged(String name, String password) {
        final Optional<Account> account = accountRepository.findByNameAndPassword(name, password);
        if(account.isEmpty()){
            return Optional.empty();
        }
        else{
            activeAccount = new AccountDto(account.get().getName(), account.get().getPermission(), account.get().isLoggedIn());
            if(activeAccount.getPermission().equals(Permission.ADMIN)){
                account.get().setLoggedIn(true);
                accountRepository.save(account.get());
                return Optional.of(activeAccount);
            }
            else{
                return Optional.empty();
            }
        }
    }

    @Override
    public boolean IsAdminOnline() {
        final Optional<Account> account = accountRepository.findByNameAndPassword("admin", "admin");
        if(account.isEmpty()){
            return false;
        }
        else{
            return account.get().isLoggedIn();
        }
    }

    @Override
    public void signOutAdmin() {
        final Optional<Account> account = accountRepository.findByNameAndPassword("admin", "admin");
        if(!account.isEmpty()){
            account.get().setLoggedIn(false);
            accountRepository.save(account.get());
        }
    }

}
