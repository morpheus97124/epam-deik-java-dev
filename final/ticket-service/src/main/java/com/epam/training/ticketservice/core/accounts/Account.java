package com.epam.training.ticketservice.core.accounts;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long Id;

    @Column(unique = true)
    private String name;

    private String password;

    private Permission permission;

    private boolean loggedIn;

    public Account(final String name, final String password, boolean adminRights){
        this.name = name;
        this.password = password;
        if(adminRights){
            this.permission = Permission.ADMIN;
        }
        else{
            this.permission = Permission.USER;
        }
        this.loggedIn = false;
    }
}
