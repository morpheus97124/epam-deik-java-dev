package com.epam.training.ticketservice.core.rooms;

public interface RoomService {
    void createRoom(String name, int rowNum, int colNum);

    void updateRoom(String name, int rowNum, int colNum);

    void deleteRoom(String name);

    String listAllRooms();
}
