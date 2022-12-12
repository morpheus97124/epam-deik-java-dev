package com.epam.training.ticketservice.core.movies;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Movie {

    public enum genres{
        action,
        adventure,
        animate,
        comedy,
        drama,
        fantasy,
        historical,
        horror,
        noir,
        scifi,
        thriller,
        western
    }

    @Id
    @GeneratedValue
    private Long Id;

    @Column(unique = true)
    private String title;

    private genres genre;

    private int length;

    public Movie(String title, genres genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    @Override
    public String toString(){
        return String.format("%s (%s, %d minutes)",
                this.title,this.genre,this.length);
    }
}
