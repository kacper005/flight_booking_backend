package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Airport;
import org.springframework.data.repository.CrudRepository;

/** Repository interface for accessing Airport data in the database. */
public interface AirportRepository extends CrudRepository<Airport, Integer> {
  /**
   * Checks if an airport with the given code exists in the database.
   *
   * @param code The code of the airport to check.
   * @return {@code true} if an airport with the given code exists, {@code false} otherwise.
   */
  boolean existsByCode(String code);
}
