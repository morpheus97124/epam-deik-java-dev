package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.core.accounts.Account;
import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.accounts.Permission;
import com.epam.training.ticketservice.core.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
//@RequiredArgsConstructor
public class AccountCommand {
    AccountService accountService;

    //@Autowired
    //AccountDto lastAccount;

    @ShellMethod(value = "Login command for the user", key = "sign in")
    public String login(final String name, final String password){
        final Optional<AccountDto> accountDto = accountService.login(name, password);
        if(accountDto.isEmpty()){
            return "Login failed due to incorrect credentials";
        }
        else{
            //lastAccount = accountDto.get();
            return String.format("%s has signed in", accountDto.get());
        }
    }

    @ShellMethod(value = "Login command for the admin", key = "sign in priviliged")
    public String loginPriviliged(final String name, final String password){
        final Optional<AccountDto> accountDto = accountService.loginPriviliged(name, password);
        if(accountDto.isEmpty()){
            return "Login failed due to incorrect credentials";
        }
        else{
            return String.format("%s has signed in", accountDto.get());
        }
    }

    @ShellMethod(value = "Describe if admin account is logged in or not", key = "describe account")
    public String describeAccount(){
        if(accountService.IsAdminOnline()){
            return "Signed in with privileged account 'admin'";
        }
        else{
            return "You are not signed in";
        }
    }

    @ShellMethod(value = "Sign out admin", key = "sign out")
    public void signOut(){
        accountService.signOutAdmin();

    }
}
