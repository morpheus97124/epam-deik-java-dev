package com.epam.training.ticketservice.core.screenings;

import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.movies.Movie;
import com.epam.training.ticketservice.core.movies.MovieRepository;
import com.epam.training.ticketservice.core.rooms.Room;
import com.epam.training.ticketservice.core.rooms.RoomRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningServiceImplTest {

    AccountService accountService;

    ScreeningServiceImpl underTest;

    Movie movie;
    Room room;
    SimpleDateFormat sdf;
    Date date1;
    Date date2;
    Date date3;
    Date date4;
    Date date5;
    String date1str = "2023-01-01 12:00";
    String date2str = "2023-01-01 19:00";
    String date3str = "2023-01-01 19:35";
    String date4str = "2023-01-02 12:00";
    String date5str = "2023-01-03 12:00";
    Screening screening1;
    Screening screening2;
    Screening screening3;
    Screening screening4;
    Screening screening5;

    @BeforeEach
    void setUp() throws ParseException {
        movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        room = new Room("Pedersoli", 10, 10);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date1 = sdf.parse(date1str);//Original
        screening1 = new Screening(movie, room, date1);

        date2 = sdf.parse(date2str);//Overlaps
        screening2 = new Screening(movie, room, date2);

        date3 = sdf.parse(date3str);//Starts during break
        screening3 = new Screening(movie, room, date3);

        date4 = sdf.parse(date3str);//Next day
        screening4 = new Screening(movie, room, date4);

        date5 = sdf.parse(date3str);//Day after that
        screening5 = new Screening(movie, room, date5);

    }

    @Test
    void createScreeningWhenAdminOnlineAndWrongTitle() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        //When
        String actualStr = underTest.createScreening(movie.getTitle()+"x", room.getName(), date1str);
        //Then
        Assertions.assertEquals("No movie by that title", actualStr);
    }
    @Test
    void createScreeningWhenAdminOnlineAndWrongRoomName() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        //When
        String actualStr = underTest.createScreening(movie.getTitle(), room.getName()+"x", date1str);
        //Then
        Assertions.assertEquals("No room by that name", actualStr);
    }
    @Test
    void createScreeningWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        //When
        String actualStr = underTest.createScreening(movie.getTitle(), room.getName(), date1str);
        //Then
        Assertions.assertEquals(null, actualStr);
    }
    @Test
    void createScreeningWhenAdminOffline() {
        //Given
        //When
        String actualStr = underTest.createScreening(movie.getTitle(), room.getName(), date1str);
        //Then
        Assertions.assertEquals("ADMIN IS OFFLINE\nCant create screening", actualStr);
    }
    @Test
    void deleteScreeningWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createScreening(movie.getTitle(), room.getName(), date1str);
        //When
        String actualStr = underTest.deleteScreening(movie.getTitle(), room.getName(), date1str);
        //Then
        Assertions.assertEquals(null, actualStr);
    }
    @Test
    void deleteScreeningWhenAdminOffline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createScreening(movie.getTitle(), room.getName(), date1str);
        underTest.accountService.signOutAdmin();
        //When
        String actualStr = underTest.deleteScreening(movie.getTitle(), room.getName(), date1str);
        //Then
        Assertions.assertEquals("ADMIN IS OFFLINE\nCant delete screening", actualStr);
    }
    @Test
    void listAllScreenings() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createScreening(movie.getTitle(), room.getName(), date1str);
        underTest.createScreening(movie.getTitle(), room.getName(), date4str);
        underTest.createScreening(movie.getTitle(), room.getName(), date5str);
        String str = screening1 + "\n" + screening4 + "\n" + screening5;
        //When
        String actualStr = underTest.listAllScreenings();
        //Then
        Assertions.assertEquals(str, actualStr);
    }
    @Test
    void handleOverlapWhenNoOverlap() {
        //Given
        //When
        String actualStr = underTest.handleOverlap(screening1);
        //Then
        Assertions.assertEquals(null, actualStr);
    }
    @Test
    void handleOverlapWhenOverlap() {
        //Given
        underTest.createScreening(screening1.getMovie().getTitle(), screening1.getRoom().getName(), date1str);
        //When
        String actualStr = underTest.createScreening(screening2.getMovie().getTitle(), screening2.getRoom().getName(), date2str);
        //Then
        Assertions.assertEquals("There is an overlapping screening", actualStr);
    }
    @Test
    void handleOverlapWhenOverlapInBreak() {
        //Given
        underTest.createScreening(screening1.getMovie().getTitle(), screening1.getRoom().getName(), date1str);
        //When
        String actualStr = underTest.createScreening(screening3.getMovie().getTitle(), screening3.getRoom().getName(), date3str);
        //Then
        Assertions.assertEquals("There is an overlapping screening", actualStr);
    }
}