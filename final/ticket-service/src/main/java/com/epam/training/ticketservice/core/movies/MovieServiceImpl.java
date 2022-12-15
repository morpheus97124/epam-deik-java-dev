package com.epam.training.ticketservice.core.movies;

import com.epam.training.ticketservice.core.accounts.AccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final AccountService accountService;

    @Override
    public void createMovie(String title, Movie.genres genre, int length){
        if(accountService.IsAdminOnline()){
            Movie movie = new Movie(title, genre, length);
            movieRepository.save(movie);
            //System.out.println(movieRepository.findByTitle(title));
        }
        else{
            System.out.println("ADMIN IS OFFLINE\nCan't register new movie");
        }
    }

    @Override
    public void updateMovie(String title, Movie.genres genre, int length) {
        if(accountService.IsAdminOnline()){
            Optional<Movie> movie = movieRepository.findByTitle(title);
            if(movie.isEmpty()){
                System.out.println("Movie not found");
            }
            else{
                movie.get().setGenre(genre);
                movie.get().setLength(length);
                movieRepository.save(movie.get());
            }
        }
        else{
            System.out.println("ADMIN IS OFFLINE\nCan't update movie");
        }
    }

    @Override
    public void deleteMovie(String title) {
        if(accountService.IsAdminOnline()){
            Optional<Movie> movie = movieRepository.findByTitle(title);
            if(!movie.isEmpty()){
                movieRepository.delete(movie.get());
            }
        }
        else{
            System.out.println("ADMIN IS OFFLINE\nCan't delete movie");
        }
    }

    @Override
    public String listAllMovies() {
        StringBuilder sb = new StringBuilder();

        for(Movie movie : movieRepository.findAll()){
            sb.append(movie + "\n");
        }
        if(sb.toString().isEmpty()){
            return "There are no movies at the moment";
        }
        else{
            return sb.toString().substring(0,sb.length()-1);
        }
    }

    public AccountService getAccountService(){
        return accountService;
    }
}
