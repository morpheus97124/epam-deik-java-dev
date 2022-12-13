package com.epam.training.ticketservice.core.screenings;

import com.epam.training.ticketservice.core.accounts.AccountService;
import com.epam.training.ticketservice.core.movies.Movie;
import com.epam.training.ticketservice.core.movies.MovieRepository;
import com.epam.training.ticketservice.core.rooms.Room;
import com.epam.training.ticketservice.core.rooms.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService{

    @Autowired
    AccountService accountService;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public String createScreening(String movieTitle, String roomName, String startDate) {
        if(accountService.IsAdminOnline()){
            //get movie
            Optional<Movie> movie = movieRepository.findByTitle(movieTitle);
            if(movie.isEmpty()){
                System.out.println("No movie by that title");
            }
            //get room
            Optional<Room> room = roomRepository.findByName(roomName);
            if(room.isEmpty()){
                System.out.println("No room by that name");
            }
            //translate date
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try{
                date = sdf.parse(startDate);
            }
            catch(ParseException e){
                System.out.println("Not a valid date");
                System.out.println(e.getMessage());
            }
            //save to repo
            Screening screening = new Screening(movie.get(), room.get(), date);
            return handleOverlap(screening);

        }
        else{
            return "ADMIN IS OFFLINE\nCant create screening room";
        }
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, String startDate) {
        Date date = null;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDate);
        }catch (ParseException e){
            System.out.println("Not a valid date");
            System.out.println(e.getMessage());
        }
        Optional<Screening> screening = screeningRepository.findScreeningByMovieTitleAndRoomNameAndAndStartDate(
                movieTitle,roomName,date);
        if(!screening.isEmpty()){
            screeningRepository.delete(screening.get());
        }
    }

    @Override
    public String listAllScreenings() {
        StringBuilder sb = new StringBuilder();

        for(Screening screening : screeningRepository.findAll()){
            sb.append(screening + "\n");
        }
        if(sb.toString().isEmpty()){
            return "There are no screenings";
        }
        else{
            return sb.toString().substring(0,sb.length()-1);
        }
    }

    String handleOverlap(Screening screening){
        Calendar calendar = Calendar.getInstance();
        for(Screening existingScreening : screeningRepository.findAll()){
            if(existingScreening.getRoom().equals(screening.getRoom())){
                Date existingScreeningStart = existingScreening.getStartDate();
                calendar.setTime(existingScreeningStart);
                Date existingScreeningEnd = DateUtils.addMinutes(existingScreeningStart,existingScreening.getMovie().getLength());
                Date screeningStart = screening.getStartDate();
                calendar.setTime(screeningStart);
                Date screeningEnds = DateUtils.addMinutes(screeningStart, screening.getMovie().getLength());
                if(existingScreeningStart.before(screeningEnds) &&
                        screeningStart.before(existingScreeningEnd)){
                    return "There is an overlapping screening";
                }
                Date existingBreakEnd = DateUtils.addMinutes(existingScreeningEnd,10);
                if(existingScreeningStart.before(screeningEnds) &&
                        screeningStart.before(existingBreakEnd)){
                    return "This would start in the break period after another screening in this room";
                }
            }
        }
        screeningRepository.save(screening);
        return null;//return "";
    }
}
