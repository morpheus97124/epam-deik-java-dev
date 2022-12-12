package com.epam.training.ticketservice.core.movies;

import com.epam.training.ticketservice.core.dto.MovieDto;

import java.util.Optional;

public interface MovieService {
    void createMovie(String title, Movie.genres genre, int length);

    void updateMovie(String title, Movie.genres genre, int length);

    void deleteMovie(String title);

    String listAllMovies();
}
