package com.epam.training.ticketservice.core.dto;

import com.epam.training.ticketservice.core.accounts.Permission;
import lombok.Value;
import org.springframework.context.annotation.Bean;

@Value
public class AccountDto {
    String name;
    Permission permission;
    boolean loggedIn;

    /*public String toString(){
        return String.format("AccountDto : name=%s, permission=%s",this.name,this.permission);
    }*/
}
