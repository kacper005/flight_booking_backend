package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Airport;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for accessing Airport data in the database.
 */
public interface AirportRepository extends CrudRepository<Airport, Integer> {
    boolean existsByCode(String code);
}
