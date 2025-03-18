package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Airline;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for accessing Airline data in the database.
 */

public interface AirlineRepository extends CrudRepository<Airline, Long> {}