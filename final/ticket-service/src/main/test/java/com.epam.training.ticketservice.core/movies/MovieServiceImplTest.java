package com.epam.training.ticketservice.core.movies;
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

import static org.mockito.ArgumentMatchers.any;

class MovieServiceImplTest {

    MovieServiceImpl underTest;

    @Mock
    MovieRepository movieRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountService accountService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new MovieServiceImpl(movieRepository, accountService);
    }

    @Test
    void createMovieWhenAdminOnline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        //When
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        //Then
        Mockito.verify(movieRepository).save(movie);
    }
    @Test
    void createMovieWhenAdminOffline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        //When
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        Mockito.verify(movieRepository,Mockito.never()).save(any());
    }
    @Test
    void updateMovieWhenAdminOnline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        Movie updatedMovie = new Movie("Sátántangó", Movie.genres.drama, 451);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle("Sátántangó")).willReturn(Optional.of(movie));
        //When
        underTest.updateMovie("Sátántangó", Movie.genres.drama, 451);
        //Then
        Mockito.verify(movieRepository).save(updatedMovie);
    }
    @Test
    void updateMovieWhenAdminOffline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        Movie updatedMovie = new Movie("Sátántangó", Movie.genres.drama, 451);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle("Sátántangó")).willReturn(Optional.of(movie));
        //When
        underTest.updateMovie("Sátántangó", Movie.genres.drama, 451);
        //Then
        Mockito.verify(movieRepository,Mockito.never()).save(updatedMovie);
    }
    @Test
    void deleteMovieWhenAdminOnline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        BDDMockito.given(movieRepository.findByTitle("Sátántangó")).willReturn(Optional.of(movie));
        //When
        underTest.deleteMovie("Sátántangó");
        //Then
        Mockito.verify(movieRepository).delete(movie);
    }
    @Test
    void deleteMovieWhenAdminOffline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        BDDMockito.given(movieRepository.findByTitle("Sátántangó")).willReturn(Optional.of(movie));
        //When
        underTest.deleteMovie("Sátántangó");
        //Then
        Mockito.verify(movieRepository,Mockito.never()).delete(movie);
    }
    @Test
    void listAllMovies(){
        //Given
        Movie movie1 = new Movie("Sátántangó", Movie.genres.drama, 450);
        Movie movie2 = new Movie("Spirited Away", Movie.genres.animation, 125);
        List<Movie> list = new ArrayList<Movie>();
        list.add(movie1);
        list.add(movie2);
        BDDMockito.given(movieRepository.findAll()).willReturn(list);
        String str = movie1.toString() + "\n" + movie2.toString();
        //When
        String actualStr = underTest.listAllMovies();
        //Then
        Assertions.assertEquals(str, actualStr);
        Mockito.verify(movieRepository).findAll();
    }
}