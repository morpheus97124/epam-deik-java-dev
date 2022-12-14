package com.epam.training.ticketservice.core.rooms;

import com.epam.training.ticketservice.core.accounts.AccountServiceImpl;
import com.epam.training.ticketservice.core.movies.Movie;
import com.epam.training.ticketservice.core.movies.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceImplTest {

    RoomServiceImpl underTest;

    @Test
    void createRoomWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        Room room = new Room("Pedersoli", 10, 10);
        //When
        underTest.createRoom("Pedersoli", 10, 10);
        //Then
        Assertions.assertEquals(room.toString(), underTest.listAllRooms());
    }
    @Test
    void createRoomWhenAdminOffline() {
        //Given
        //When
        underTest.createRoom("Pedersoli", 10, 10);
        //Then
        Assertions.assertEquals("There are no rooms at the moment", underTest.listAllRooms());
    }
    @Test
    void updateRoomWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createRoom("Pedersoli", 10, 10);
        Room room = new Room("Pedersoli", 20, 20);
        //When
        underTest.updateRoom("Pedersoli", 20, 20);
        //Then
        Assertions.assertEquals(room.toString(), underTest.listAllRooms());
    }
    @Test
    void updateRoomWhenAdminOffline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createRoom("Pedersoli", 10, 10);
        Room room = new Room("Pedersoli", 10, 10);
        underTest.accountService.signOutAdmin();
        //When
        underTest.updateRoom("Pedersoli", 20, 20);
        //Then
        Assertions.assertEquals(room.toString(), underTest.listAllRooms());
    }
    @Test
    void deleteRoomWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createRoom("Pedersoli", 10, 10);
        //When
        underTest.deleteRoom("Pedersoli");
        //Then
        Assertions.assertEquals("There are no rooms at the moment", underTest.listAllRooms());

    }
    @Test
    void deleteRoomWhenAdminOffline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createRoom("Pedersoli", 10, 10);
        Room room = new Room("Pedersoli", 10, 10);
        underTest.accountService.signOutAdmin();
        //When
        underTest.deleteRoom("Pedersoli");
        //Then
        Assertions.assertEquals(room.toString(), underTest.listAllRooms());
    }
    @Test
    void listAllRooms() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createRoom("Pedersoli", 10, 10);
        underTest.createRoom("Girotti", 15, 15);
        Room room1 = new Room("Pedersoli", 10, 10);
        Room room2 = new Room("Girotti", 15, 15);
        String str = room1 + "\n" + room2 + "\n";
        //When
        String actualStr = underTest.listAllRooms();
        //Then
        Assertions.assertEquals(str, actualStr);
    }
}