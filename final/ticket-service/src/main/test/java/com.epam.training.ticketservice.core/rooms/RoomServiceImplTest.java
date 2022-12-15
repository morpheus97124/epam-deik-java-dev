package com.epam.training.ticketservice.core.rooms;

import com.epam.training.ticketservice.core.accounts.AccountRepository;
import com.epam.training.ticketservice.core.accounts.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RoomServiceImplTest {

    RoomServiceImpl underTest;

    @Mock
    RoomRepository roomRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountService accountService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new RoomServiceImpl(roomRepository, accountService);
    }

    @Test
    void createRoomWhenAdminOnline() {
        //Given
        Room room = new Room("Pedersoli", 10, 10);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        //When
        underTest.createRoom("Pedersoli", 10, 10);
        //Then
        Mockito.verify(roomRepository).save(room);
    }
    @Test
    void createRoomWhenAdminOffline() {
        //Given
        Room room = new Room("Pedersoli", 10, 10);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        //When
        underTest.createRoom("Pedersoli", 10, 10);
        //Then
        Mockito.verify(roomRepository,Mockito.never()).save(room);
    }
    @Test
    void updateRoomWhenAdminOnline() {
        //Given
        Room room = new Room("Pedersoli", 10, 10);
        Room room2 = new Room("Pedersoli", 20, 20);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        BDDMockito.given(roomRepository.findByName("Pedersoli")).willReturn(Optional.of(room));
        //When
        underTest.updateRoom("Pedersoli", 20, 20);
        //Then
        Mockito.verify(roomRepository).save(room2);
    }
    @Test
    void updateRoomWhenAdminOffline() {
        //Given
        Room room = new Room("Pedersoli", 10, 10);
        Room room2 = new Room("Pedersoli", 20, 20);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        BDDMockito.given(roomRepository.findByName("Pedersoli")).willReturn(Optional.of(room));
        //When
        underTest.updateRoom("Pedersoli", 20, 20);
        //Then
        Mockito.verify(roomRepository, Mockito.never()).save(room2);
    }
    @Test
    void deleteRoomWhenAdminOnline() {
        //Given
        Room room = new Room("Pedersoli", 10, 10);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        BDDMockito.given(roomRepository.findByName("Pedersoli")).willReturn(Optional.of(room));
        //When
        underTest.deleteRoom("Pedersoli");
        //Then
        Mockito.verify(roomRepository).delete(room);
    }
    @Test
    void deleteRoomWhenAdminOffline() {
        //Given
        Room room = new Room("Pedersoli", 10, 10);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        BDDMockito.given(roomRepository.findByName("Pedersoli")).willReturn(Optional.of(room));
        //When
        underTest.deleteRoom("Pedersoli");
        //Then
        Mockito.verify(roomRepository, Mockito.never()).delete(room);
    }
    @Test
    void listAllRooms() {
        //Given
        Room room1 = new Room("Pedersoli", 10, 10);
        Room room2 = new Room("Girotti", 20, 20);
        List<Room> list = new ArrayList<Room>();
        list.add(room1);
        list.add(room2);
        BDDMockito.given(roomRepository.findAll()).willReturn(list);
        String str = room1.toString() + "\n" + room2.toString();
        //When
        String actualStr = underTest.listAllRooms();
        //Then
        Assertions.assertEquals(str, actualStr);
        Mockito.verify(roomRepository).findAll();
    }
}