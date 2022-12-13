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
        return null;
    }

    @ShellMethod(value = "Login command for the admin", key = "sign in privileged")
    public String loginPrivileged(final String name, final String password){
        final Optional<AccountDto> accountDto = accountService.loginPriviliged(name, password);
        if(accountDto.isEmpty()){
            return "Login failed due to incorrect credentials";
        }
        return null;
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


    @ShellMethod(value = "Shutdown application", key = "exit")
    public void exit(){
        System.exit(0);
    }
}
