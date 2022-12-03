package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.dto.AccountDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class AccountCommand {
    AccountService accountService;

    @ShellMethod(value = "Login command for the user", key = "sign in")
    public String login(final String name, final String password){
        final Optional<AccountDto> accountDto = accountService.login(name, password);
        if(accountDto.isEmpty()){
            return "Login failed due to incorrect credentials";
        }
        else{
            return String.format("%s has signed in", accountDto.get());
        }
    }
}
