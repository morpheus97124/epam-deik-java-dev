package com.epam.training.ticketservice.core.rooms;

import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.movies.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    AccountService accountService;

    @Override
    public void createRoom(String name, int rowNum, int colNum) {
        if(accountService.IsAdminOnline()){
            Room room = new Room(name, rowNum, colNum);
            roomRepository.save(room);
        }
        else{
            System.out.println("ADMIN IS OFFLINE\nCant register new room");
        }
    }

    @Override
    public void updateRoom(String name, int rowNum, int colNum) {
        if(accountService.IsAdminOnline()){
            Optional<Room> room = roomRepository.findByName(name);
            if(room.isEmpty()){
                System.out.println("Room not found");
            }
            else{
                room.get().setMaxRowNum(rowNum);
                room.get().setMaxColNum(colNum);
                roomRepository.save(room.get());
            }
        }
        else{
            System.out.println("ADMIN IS OFFLINE\nCant update room");
        }
    }

    @Override
    public void deleteRoom(String name) {
        if(accountService.IsAdminOnline()){
            Optional<Room> room = roomRepository.findByName(name);
            if(room.isEmpty()){
                System.out.println("Room not found");
            }
            else{
                roomRepository.delete(room.get());
            }
        }
        else{
            System.out.println("ADMIN IS OFFLINE\nCant delete room");
        }
    }

    @Override
    public String listAllRooms() {
        StringBuilder sb = new StringBuilder();

        for(Room room : roomRepository.findAll()){
            sb.append(room + "\n");
        }
        if(sb.toString().isEmpty()){
            return "There are no rooms at the moment";
        }
        else{
            return sb.toString().substring(0,sb.length()-1);
        }
    }
}
