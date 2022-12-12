package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.core.rooms.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class RoomCommand {

    RoomService roomService;

    @ShellMethod(value = "Create new room in theater DB", key = "create room")
    public void createRoom(String name, int rowNum, int colNum){
        roomService.createRoom(name, rowNum, colNum);
    }

    @ShellMethod(value = "Update room in theater DB", key = "update room")
    public void updateRoom(String name, int rowNum, int colNum){
        roomService.updateRoom(name, rowNum, colNum);
    }

    @ShellMethod(value = "Delete room from theater DB", key = "delete room")
    public void deleteRoom(String name){
        roomService.deleteRoom(name);
    }

    @ShellMethod(value = "List all rooms from theater DB", key = "list rooms")
    public String listAllRooms(){
        return roomService.listAllRooms();
    }
}
