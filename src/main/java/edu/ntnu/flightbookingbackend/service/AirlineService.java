package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Airline;
import edu.ntnu.flightbookingbackend.repository.AirlineRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic related to airlines.
 */

@Service
@Tag(name = "Airline Service", description = "Business logic related to airlines")
public class AirlineService {
  @Autowired
  private AirlineRepository airlineRepository;

    /**
     * Get all airlines from the application state.
     *
     * @return A list of airlines, empty list if there are none
     */
    @Operation(summary = "Get all airlines", description = "Get all airlines from the application state")
    public Iterable<Airline> getAll() {
        return airlineRepository.findAll();
    }

    /**
     * Finds an airline by ID. Returns the airline if found, null otherwise.
     *
     * @param id ID of the airline to find
     * @return The airline or null if none found by the given ID
     */
    @Operation(summary = "Find airline by ID", description = "Fetches an airline based on the provided ID")
    public Airline findByID(int id) {
        Optional<Airline> airline = airlineRepository.findById(id);
        return airline.orElse(null);
    }

    /**
     * Add an airline to the application state (persist in the database).
     *
     * @param airline Airline to persist
     * @return {@code true} when airline is added, {@code false} on error
     */

    @Operation(summary = "Add a new airline", description = "Add a new airline to the application state")
    public boolean add(Airline airline) {
        boolean added = false;
        boolean airlineExists = false;

        if (airline != null) {
            Airline existingAirline = findByID(airline.getAirlineId());

            for (Airline a : airlineRepository.findAll()) {
                if (a.getAirlineId() == airline.getAirlineId()) {
                    airlineExists = true;
                }
            }

            if (!airlineExists) {
                airlineRepository.save(airline);
                added = true;
            }
        }

        return added;
    }


  /**
   * Update an airline in the application state (persist in the database).
   *
   * @param airline Airline to update
   * @return {@code true} when airline is updated, {@code false} on error
   */
    @Operation(summary = "Update an airline", description = "Update an airline in the application state")
    public boolean update(Airline airline) {
        boolean updated = false;
        boolean airlineExists = false;

        if (airline != null) {
            Airline existingAirline = findByID(airline.getAirlineId());

            for (Airline a : airlineRepository.findAll()) {
                if (a.getAirlineId() == airline.getAirlineId()) {
                    airlineExists = true;
                }
            }

            if (airlineExists) {
                airlineRepository.save(airline);
                updated = true;
            }
        }

        return updated;
    }


  /**
   * Remove an airline from the application state (database).
   *
   * @param airlineId ID of the airline to delete
   * @return {@code true} when airline is deleted, {@code false} when airline was not found in the
   *         database
   *
   */

    @Operation(summary = "Remove an airline", description = "Remove an airline from the application state")
    public boolean remove(int airlineId) {
        boolean removed = false;
        Airline airline = findByID(airlineId);

        if (airline != null) {
            airlineRepository.delete(airline);
            removed = true;
        }

        return removed;
    }
}