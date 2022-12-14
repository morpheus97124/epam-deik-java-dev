package com.epam.training.ticketservice.core.movies;

import com.epam.training.ticketservice.core.accounts.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceImplTest {

    MovieServiceImpl underTest;

    @Test
    void createMovieWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        //When
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        //Then
        Assertions.assertEquals(movie.toString(), underTest.listAllMovies());
    }
    @Test
    void createMovieWhenAdminOffline() {
        //Given
        //When
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        //Then
        Assertions.assertEquals("There are no movies at the moment",underTest.listAllMovies());
    }
    @Test
    void updateMovieWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        Movie updatedMovie = new Movie("Sátántangó", Movie.genres.drama, 451);
        //When
        underTest.updateMovie("Sátántangó", Movie.genres.drama, 451);
        //Then
        Assertions.assertEquals(updatedMovie.toString(), underTest.listAllMovies());
    }
    @Test
    void updateMovieWhenAdminOffline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        Movie updatedMovie = new Movie("Sátántangó", Movie.genres.drama, 450);
        underTest.accountService.signOutAdmin();
        //When
        underTest.updateMovie("Sátántangó", Movie.genres.drama, 451);
        //Then
        Assertions.assertEquals(updatedMovie.toString(), underTest.listAllMovies());
    }
    @Test
    void deleteMovieWhenAdminOnline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        //When
        underTest.deleteMovie(movie.getTitle());
        //Then
        Assertions.assertEquals("There are no movies at the moment",underTest.listAllMovies());
    }
    @Test
    void deleteMovieWhenAdminOffline() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        Movie movie = new Movie("Sátántangó", Movie.genres.drama, 450);
        underTest.accountService.signOutAdmin();
        //When
        underTest.deleteMovie(movie.getTitle());
        //Then
        Assertions.assertEquals(movie.toString(),underTest.listAllMovies());
    }

    @Test
    void listAllMovies() {
        //Given
        underTest.accountService.loginPriviliged("admin", "admin");
        underTest.createMovie("Sátántangó", Movie.genres.drama, 450);
        underTest.createMovie("Spirited Away", Movie.genres.animation, 100);
        Movie movie1 = new Movie("Sátántangó", Movie.genres.drama, 450);
        Movie movie2 = new Movie("Spirited Away", Movie.genres.animation, 100);
        String str = movie1 + "\n" + movie2 + "\n";
        //When
        String actualStr = underTest.listAllMovies();
        //Then
        Assertions.assertEquals(str, actualStr);
    }
}