package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.core.movies.Movie;
import com.epam.training.ticketservice.core.movies.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {

    MovieService movieService;

    @ShellMethod(value = "Create a movie in the DB", key = "create movie")
    public void createMovie(String title, Movie.genres genre, int length){
        movieService.createMovie(title, genre, length);
    }

    @ShellMethod(value = "Update movie in the DB", key = "update movie")
    public void updateMovie(String title, Movie.genres genre, int length){
        movieService.updateMovie(title, genre, length);
    }

    @ShellMethod(value = "Delete movie from the DB", key = "delete movie")
    public void deleteMovie(String title){
        movieService.deleteMovie(title);
    }

    @ShellMethod(value = "List all movies from the DB", key = "list movies")
    public String listAllMovies(){
        return movieService.listAllMovies();
    }
}
