package com.epam.training.ticketservice.core.movies;
import com.epam.training.ticketservice.core.accounts.Account;
import com.epam.training.ticketservice.core.accounts.AccountRepository;
import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.accounts.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;

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
        //accountService = new AccountServiceImpl(accountRepository);
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
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        //When
        underTest.updateMovie("Sátántangó", Movie.genres.drama, 450);
        //Then
        Mockito.verify(movieRepository).save(movie);
        Mockito.verify(movieRepository).findByTitle(movie.getTitle());
    }
    @Test
    void updateMovieWhenAdminOffline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        //When
        underTest.updateMovie("Sátántangó", Movie.genres.drama, 450);
        //Then
        Mockito.verify(movieRepository).save(movie);
        Mockito.verify(movieRepository,Mockito.never()).findByTitle(movie.getTitle());
    }
    @Test
    void deleteMovieWhenAdminOnline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        //When
        underTest.deleteMovie("Sátántangó");
        //Then
        Mockito.verify(movieRepository).save(movie);
        Mockito.verify(movieRepository).findByTitle(movie.getTitle());
    }
    @Test
    void deleteMovieWhenAdminOffline() {
        //Given
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(true);
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        BDDMockito.given(accountService.IsAdminOnline()).willReturn(false);
        //When
        underTest.deleteMovie("Sátántangó");
        //Then
        Mockito.verify(movieRepository).save(movie);
        Mockito.verify(movieRepository,Mockito.never()).findByTitle(movie.getTitle());
    }
}