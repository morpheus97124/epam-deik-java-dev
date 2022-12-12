package com.epam.training.ticketservice.core.screenings;

public interface ScreeningService {

    String createScreening(String movieTitle, String roomName, String startDate);

    String listAllScreenings();

    void deleteScreening(String movieTitle, String roomName, String startDate);
}
