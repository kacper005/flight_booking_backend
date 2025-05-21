package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Booking;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/** Repository interface for accessing Booking data in the database. */
public interface BookingRepository extends CrudRepository<Booking, Integer> {
  /**
   * Find all bookings associated with a specific user.
   *
   * @param userId ID of the user whose bookings are to be retrieved
   * @return List of bookings associated with the specified user
   */
  List<Booking> findByUserUserId(Integer userId);
}
