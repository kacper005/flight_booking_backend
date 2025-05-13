package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Flight;
import org.springframework.data.repository.CrudRepository;

/** Repository interface for accessing Flight data in the database. */
public interface FlightRepository extends CrudRepository<Flight, Integer> {
  /**
   * Checks if a flight with the given flight number exists in the database.
   *
   * @param s The flight number to check.
   * @return {@code true} if a flight with the given flight number exists, {@code false} otherwise.
   */
  boolean existsByFlightNumber(String s);
}
