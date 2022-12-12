package com.epam.training.ticketservice.core.screenings;

import com.epam.training.ticketservice.core.movies.Movie;
import com.epam.training.ticketservice.core.rooms.Room;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue
    private int Id;

    @OneToOne
    private Movie movie;

    @OneToOne
    private Room room;

    private Date startDate;

    public Screening(Movie movie, Room room, Date startDate) {
        this.movie = movie;
        this.room = room;
        this.startDate = startDate;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.movie + ", screened in room ");
        sb.append(this.room.getName() + ", at ");
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startDate));
        return sb.toString();
    }
}
