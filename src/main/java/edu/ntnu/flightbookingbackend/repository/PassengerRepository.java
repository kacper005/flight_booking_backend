package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Passenger;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for accessing Passenger data in the database.
 */
public interface PassengerRepository extends CrudRepository<Passenger, Integer> {
}
