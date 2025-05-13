package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Airport;
import edu.ntnu.flightbookingbackend.repository.AirportRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Business logic related to airports. */
@Service
@Tag(name = "Airport Service", description = "Business logic related to airports")
public class AirportService {
  @Autowired private AirportRepository airportRepository;

  /**
   * Get all airports from the application state.
   *
   * @return A list of airports, empty list if there are none
   */
  @Operation(
      summary = "Get all airports",
      description = "Get all airports from the application state")
  public Iterable<Airport> getAll() {
    return airportRepository.findAll();
  }

  /**
   * Finds an airport by ID. Returns the airport if found, null otherwise.
   *
   * @param id ID of the airport to find
   * @return The airport or null if none found by the given ID
   */
  @Operation(
      summary = "Find airport by ID",
      description = "Fetches an airport based on the provided ID")
  public Airport findById(int id) {
    Optional<Airport> airport = airportRepository.findById(id);
    return airport.orElse(null);
  }

  /**
   * Add an airport to the application state (persist in the database).
   *
   * @param airport Airport to persist
   * @return {@code true} when airport is added, {@code false} on error
   */
  @Operation(
      summary = "Add a new airport",
      description = "Add a new airport to the application state")
  public boolean add(Airport airport) {
    boolean added = false;
    boolean airportExists = false;

    if (airport != null) {

      for (Airport a : airportRepository.findAll()) {
        if (a.getAirportId() == airport.getAirportId()) {
          airportExists = true;
        }
      }

      if (!airportExists) {
        airportRepository.save(airport);
        added = true;
      }
    }
    return added;
  }

  /**
   * Update an airport in the application state (persist in the database).
   *
   * @param airportId ID of the airport to update
   * @param airport Airport data to update
   * @return {@code true} when airport is updated, {@code false} on error
   */
  @Operation(
      summary = "Update an airport",
      description = "Update an airport in the application state")
  public String update(int airportId, Airport airport) {
    String errorMessage = null;
    Airport existingAirport = findById(airportId);
    if (existingAirport == null) {
      errorMessage = "No airport with ID " + airportId + " found.";
    } else if (airport == null) {
      errorMessage = "No airport data provided.";
    } else if (airport.getAirportId() != airportId) {
      errorMessage = "Airport ID in the data does not match the ID in the path.";
    }

    if (errorMessage == null) {
      airportRepository.save(airport);
    }
    return errorMessage;
  }

  /**
   * Remove an airport from the application state (database).
   *
   * @param airportId ID of the airport to delete
   * @return {@code true} when airport is deleted, {@code false} when airport was not found in the
   *     database
   */
  @Operation(
      summary = "Remove an airport",
      description = "Remove an airport from the application state")
  public boolean remove(int airportId) {
    Optional<Airport> airport = airportRepository.findById(airportId);
    if (airport.isPresent()) {
      airportRepository.delete(airport.get());
    }
    return airport.isPresent();
  }

  /** Removes all airports from the application state (database). */
  @Operation(
      summary = "Removes all airports",
      description = "Removes all airports from the application state")
  public void removeAll() {
    airportRepository.deleteAll();
  }

  /**
   * Check if an airport exists in the database.
   *
   * @param airportId ID of the airport to check
   * @return {@code true} if the airport exists, {@code false} otherwise
   */
  public boolean airportExists(Integer airportId) {
    return airportRepository.existsById(airportId);
  }
}
