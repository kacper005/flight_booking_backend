package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Flight;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
}
