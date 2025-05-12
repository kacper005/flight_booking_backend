package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Airline;
import org.springframework.data.repository.CrudRepository;

/** Repository interface for accessing Airline data in the database. */
public interface AirlineRepository extends CrudRepository<Airline, Integer> {
  /**
   * Checks if an airline with the given code exists in the database.
   *
   * @param code The code of the airline to check.
   * @return {@code true} if an airline with the given code exists, {@code false} otherwise.
   */
  boolean existsByCode(String code);
}
