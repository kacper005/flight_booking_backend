package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Booking;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/** Repository interface for accessing Booking data in the database. */
public interface BookingRepository extends CrudRepository<Booking, Integer> {
  List<Booking> findByUserUserId(Integer userId);
}
