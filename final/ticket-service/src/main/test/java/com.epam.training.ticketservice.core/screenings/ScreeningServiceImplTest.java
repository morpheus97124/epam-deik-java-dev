package com.epam.training.ticketservice.core.screenings;

import com.epam.training.ticketservice.core.accounts.AccountRepository;
import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.movies.Movie;
import com.epam.training.ticketservice.core.movies.MovieRepository;
import com.epam.training.ticketservice.core.rooms.Room;
import com.epam.training.ticketservice.core.rooms.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class ScreeningServiceImplTest {

    ScreeningServiceImpl underTest;

    @Mock
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    ScreeningRepository screeningRepository;

    @Mock
    MovieRepository movieRepository;

    @Mock
    RoomRepository roomRepository;

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

        MockitoAnnotations.openMocks(this);
        underTest = new ScreeningServiceImpl(accountService, screeningRepository, movieRepository, roomRepository);

        movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        room = new Room("Pedersoli", 10, 10);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date1 = sdf.parse(date1str);//Original
        screening1 = new Screening(movie, room, date1);

        date2 = sdf.parse(date2str);//Overlaps
        screening2 = new Screening(movie, room, date2);

        date3 = sdf.parse(date3str);//Starts during break
        screening3 = new Screening(movie, room, date3);

        date4 = sdf.parse(date4str);//Next day
        screening4 = new Screening(movie, room, date4);

        date5 = sdf.parse(date5str);//Day after that
        screening5 = new Screening(movie, room, date5);

    }

    @Test
    void createScreeningWhenAdminOnlineAndWrongTitle() {
        //Given
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        //When
        String actualStr = underTest.createScreening(movie.getTitle()+"x", room.getName()+"x", date1str);
        //Then
        Assertions.assertEquals("No movie by that title", actualStr);
        Mockito.verify(screeningRepository, Mockito.never()).save(screening1);
    }
    @Test
    void createScreeningWhenAdminOnlineAndWrongRoomName() {
        //Given
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(movie.getTitle())).willReturn(Optional.ofNullable(movie));
        //When
        String actualStr = underTest.createScreening(movie.getTitle(), room.getName()+"x", date1str);
        //Then
        Assertions.assertEquals("No room by that name", actualStr);
        Mockito.verify(screeningRepository, Mockito.never()).save(screening1);
    }
    @Test
    void createScreeningWhenAdminOnline() {
        //Given
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle(movie.getTitle())).willReturn(Optional.ofNullable(movie));
        BDDMockito.given(roomRepository.findByName(room.getName())).willReturn(Optional.of(room));
        //When
        underTest.createScreening(movie.getTitle(), room.getName(), date1str);
        //Then
        //Assertions.assertEquals(null, actualStr);
        Mockito.verify(screeningRepository).save(screening1);
    }
    @Test
    void createScreeningWhenAdminOffline() {
        //Given
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        //When
        String actualStr = underTest.createScreening(movie.getTitle(), room.getName(), date1str);
        //Then
        Assertions.assertEquals("ADMIN IS OFFLINE\nCant create screening", actualStr);
        Mockito.verify(screeningRepository, Mockito.never()).save(screening1);
    }
   @Test
    void deleteScreeningWhenAdminOnline() {
       //Given
       BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
       BDDMockito.given(movieRepository.findByTitle(movie.getTitle())).willReturn(Optional.ofNullable(movie));
       BDDMockito.given(roomRepository.findByName(room.getName())).willReturn(Optional.of(room));
       BDDMockito.given(screeningRepository.
               findScreeningByMovieTitleAndRoomNameAndAndStartDate(movie.getTitle(),
                       room.getName(), date1)).willReturn(Optional.ofNullable(screening1));
       //When
       underTest.deleteScreening(movie.getTitle(), room.getName(), date1str);
       //Then
       //Assertions.assertEquals(null, actualStr);
       Mockito.verify(screeningRepository).delete(screening1);
    }
    @Test
    void deleteScreeningWhenAdminOffline() {
        //Given
       BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
       BDDMockito.given(movieRepository.findByTitle(movie.getTitle())).willReturn(Optional.ofNullable(movie));
       BDDMockito.given(roomRepository.findByName(room.getName())).willReturn(Optional.of(room));
       BDDMockito.given(screeningRepository.
               findScreeningByMovieTitleAndRoomNameAndAndStartDate(movie.getTitle(),
                       room.getName(), date1)).willReturn(Optional.ofNullable(screening1));
       //When
       underTest.deleteScreening(movie.getTitle(), room.getName(), date1str);
       //Then
       //Assertions.assertEquals(null, actualStr);
       Mockito.verify(screeningRepository,Mockito.never()).delete(screening1);
    }
    @Test
    void listAllScreenings() {
        //Given
        List<Screening> list = new ArrayList<Screening>();
        list.add(screening1);
        list.add(screening4);
        list.add(screening5);
        BDDMockito.given(screeningRepository.findAll()).willReturn(list);
        String str = screening1 + "\n" + screening4 + "\n" + screening5;
        //When
        String actualStr = underTest.listAllScreenings();
        //Then
        Assertions.assertEquals(str, actualStr);
        Mockito.verify(screeningRepository).findAll();
    }
    @Test
    void handleOverlapWhenNoOverlap() {
        //Given
        List<Screening> list = new ArrayList<Screening>();
        list.add(screening1);
        BDDMockito.given(screeningRepository.findAll()).willReturn(list);
        //When
        String actualStr = underTest.handleOverlap(screening4);
        //Then
        Assertions.assertEquals(null, actualStr);
        Mockito.verify(screeningRepository).save(screening4);
    }
    @Test
    void handleOverlapWhenOverlap() {
        //Given
        List<Screening> list = new ArrayList<Screening>();
        list.add(screening1);
        BDDMockito.given(screeningRepository.findAll()).willReturn(list);
        //When
        String actualStr = underTest.handleOverlap(screening2);
        //Then
        Assertions.assertEquals("There is an overlapping screening", actualStr);
        Mockito.verify(screeningRepository, Mockito.never()).save(screening2);
    }
    @Test
    void handleOverlapWhenOverlapInBreak() {
        //Given
        List<Screening> list = new ArrayList<Screening>();
        list.add(screening1);
        BDDMockito.given(screeningRepository.findAll()).willReturn(list);
        //When
        String actualStr = underTest.handleOverlap(screening3);
        //Then
        Assertions.assertEquals("This would start in the break period after another screening in this room", actualStr);
        Mockito.verify(screeningRepository, Mockito.never()).save(screening3);
    }
}