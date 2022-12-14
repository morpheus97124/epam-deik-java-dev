package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.core.screenings.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommand {

    ScreeningService screeningService;

    @ShellMethod(value = "Create new screening", key = "create screening")
    public String createScreening(String movieTitle, String roomName, String startDate){
        return screeningService.createScreening(movieTitle, roomName, startDate);
    }

    @ShellMethod(value = "Delete screaning", key = "delete screening")
    public String updateScreening(String movieTitle, String roomName, String startDate){
        return screeningService.deleteScreening(movieTitle, roomName, startDate);
    }

    @ShellMethod(value = "List all screenings", key = "list screenings")
    public String listAllScreenings(){
        return screeningService.listAllScreenings();
    }
}
