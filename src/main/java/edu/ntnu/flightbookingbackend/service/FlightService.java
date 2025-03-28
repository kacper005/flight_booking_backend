package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.repository.FlightRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Business logic related to flights.
 */

@Service
@Tag(name = "Flight Service", description = "Business logic related to flights")
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    /**
     * Get all flights from the application state.
     *
     * @return A list of flights, empty list if there are none
     */
    @Operation(summary = "Get all flights", description = "Get all flights from the application state")
    public Iterable<Flight> getAll() {
        return flightRepository.findAll();
    }

    /**
     * Finds a flight by ID. Returns the flight if found, null otherwise.
     *
     * @param id ID of the flight to find
     * @return The flight or null if none found by the given ID
     */
    @Operation(summary = "Find flight by ID", description = "Fetches a flight based on the provided ID")
    public Flight findByID(int id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.orElse(null);
    }

    /**
     * Add a flight to the application state (persist in the database).
     *
     * @param flight Flight to persist
     * @return {@code true} when flight is added, {@code false} on error
     */
    @Operation(summary = "Add a new flight", description = "Add a new flight to the application state")
    public boolean add(Flight flight) {
        boolean added = false;
        boolean flightExists = false;

        if (flight != null) {
        Flight existingFlight = findByID(flight.getFlightId());

        for (Flight f : flightRepository.findAll()) {
            if (f.getFlightId() == flight.getFlightId()) {
            flightExists = true;
            }
        }

        if (!flightExists) {
            flightRepository.save(flight);
            added = true;
        }
        }
        return added;
    }

    /**
     * Update a flight in the application state (update in the database).
     *
     * @param flightId ID of the flight to update
     * @param flight Flight data to update
     * @return Success message or error message on failure
     */
    @Operation(summary = "Update a flight", description = "Update a flight in the application state")
    public String update(int flightId, Flight flight) {
        if (flight == null) {
            return "No flight data provided.";
        }

        Flight existingFlight = findByID(flightId);
        if (existingFlight == null) {
            return "No flight with id " + flightId + " found.";
        }

        flight.setFlightId(flightId);
        flightRepository.save(flight);
        return "Flight updated successfully.";
    }


    /**
     * Remove a flight from the application state (delete from the database).
     *
     * @param flightId ID of the flight to remove
     * @return {@code true} when flight is removed, {@code false} on error
     */
    @Operation(summary = "Remove a flight", description = "Remove a flight from the application state")
    public boolean remove(int flightId) {
        boolean removed = false;
        Flight flight = findByID(flightId);

        if (flight != null) {
            flightRepository.delete(flight);
            removed = true;
        }

        return removed;
    }

    /**
     * Removes all flights from the application state.
     */
    @Operation(summary = "Remove all flights",
        description = "Remove all flights from the application state")
    public void removeAll() {
        flightRepository.deleteAll();
    }

    /**
     * Check if a flight exists in the application state.
     *
     * @param flightId ID of the flight to check
     * @return {@code true} if the flight exists, {@code false} otherwise
     */
    public boolean flightExists(int flightId) {
        return flightRepository.existsById(flightId);
    }
}