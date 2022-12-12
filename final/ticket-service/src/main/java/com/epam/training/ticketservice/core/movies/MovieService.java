package com.epam.training.ticketservice.core.movies;

public interface MovieService {
    void createMovie(String title, Movie.genres genre, int length);

    void updateMovie(String title, Movie.genres genre, int length);

    void deleteMovie(String title);

    String listAllMovies();
}
