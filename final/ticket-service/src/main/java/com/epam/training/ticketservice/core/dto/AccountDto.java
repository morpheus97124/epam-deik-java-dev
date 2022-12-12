package com.epam.training.ticketservice.core.dto;

import com.epam.training.ticketservice.core.accounts.Permission;
import lombok.Value;

@Value
public class AccountDto {
    String name;
    Permission permission;
    boolean loggedIn;
}
