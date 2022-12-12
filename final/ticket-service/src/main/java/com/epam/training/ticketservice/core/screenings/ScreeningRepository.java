package com.epam.training.ticketservice.core.screenings;

import org.springframework.data.repository.CrudRepository;
import java.util.Date;
import java.util.Optional;

public interface ScreeningRepository extends CrudRepository<Screening, Long> {
    Optional<Screening> findScreeningByMovieTitleAndRoomNameAndAndStartDate(String movieTitle,
                                                                            String roomName,
                                                                            Date startDate);
}
