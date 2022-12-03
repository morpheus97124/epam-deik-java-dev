package com.epam.training.ticketservice.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellCommand {

    @ShellMethod(value = "Greet user", key = "greet user")
    public String greet(String name){
        return "Hello " + name + "!";
    }
}
