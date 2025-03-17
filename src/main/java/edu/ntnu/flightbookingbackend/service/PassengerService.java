package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Passenger;
import edu.ntnu.flightbookingbackend.repository.PassengerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic related to passengers.
 */
@Service
@Tag(name = "Passenger Service", description = "Business logic related to passengers")
public class PassengerService {
  @Autowired
  private PassengerRepository passengerRepository;

  /**
   * Get all passengers from the application state.
   *
   * @return A list of passengers, empty list if there are none
   */
  @Operation(summary = "Get all passengers",
      description = "Get all passengers from the application state")
  public Iterable<Passenger> getAll() {
    return passengerRepository.findAll();
  }

  /**
   * Finds a passenger by ID. Returns the passenger if found, null otherwise.
   *
   * @param id ID of the passenger to find
   * @return The passenger or null if none found by the given ID
   */
  @Operation(summary = "Find passenger by ID",
      description = "Fetches a passenger based on the provided ID")
  public Passenger findByID(int id) {
    Optional<Passenger> passenger = passengerRepository.findById(id);
    return passenger.orElse(null);
  }

  /**
   * Add a passenger to the application state (persist in the database). The same passenger cannot
   * be added to the same booking ID more than once.
   *
   * @param passenger Passenger to persist
   * @return {@code true} when passenger is added, {@code false} on error
   */
  @Operation(summary = "Add a new passenger",
      description = "Add a new passenger to the application state. The same passenger cannot be " +
          "added to the same booking ID more than once.")
  public boolean add(Passenger passenger) {
    boolean added = false;
    boolean bookingIdExists = false;
    boolean multiplePassengers = false;

    if (passenger != null) {
      Passenger existingPassenger = findByID(passenger.getPassengerId());

      // Check if the booking id already exists in the database
      BookingService bookingService = new BookingService();
      if (bookingService.findByID(passenger.getBookingId()) != null) {
        bookingIdExists = true;
      }

      // Checks that a booking ID does not have two of the same passengers with the same passport
      // number
      for (Passenger p : passengerRepository.findAll()) {
        if (p.getBookingId() == passenger.getBookingId() &&
            p.getPassportNumber().equals(passenger.getPassportNumber())) {
          multiplePassengers = true;
        }
      }

      // Add the passenger if it does not already exist and the booking id is unique
      if (bookingIdExists && !multiplePassengers) {
        passengerRepository.save(passenger);
        added = true;
      }
    }
    return added;
  }

  /**
   * Remove a passenger from the application state (database).
   *
   * @param passengerId ID of the passenger to delete
   * @return {@code true} when passenger is deleted, {@code false} when passenger was not found in
   * the database
   */
  @Operation(summary = "Remove a passenger",
      description = "Remove a passenger from the application state")
  public boolean remove(int passengerId) {
    Optional<Passenger> passenger = passengerRepository.findById(passengerId);
    if (passenger.isPresent()) {
      passengerRepository.delete(passenger.get());
    }
    return passenger.isPresent();
  }

  /**
   * Update a passenger in the application state (database).
   *
   * @param passengerId ID of the passenger to update
   * @param passenger Passenger data to update
   * @return Null on success, error message on error
   */
  @Operation(summary = "Update a passenger",
      description = "Update the details of a passenger in the application state")
  public String update(int passengerId, Passenger passenger) {
    String errorMessage = null;
    Passenger existingPassenger = findByID(passengerId);
    BookingService bookingService = new BookingService();
    if (existingPassenger == null) {
      errorMessage = "No passenger with id " + passengerId + " found.";
    } else if (passenger == null) {
      errorMessage = "No passenger data provided.";
    } else if (passenger.getPassengerId() != passengerId) {
      errorMessage = "Passenger ID does not match passenger ID in JSON data (response body).";
    } else if (bookingService.findByID(passenger.getBookingId()) == null) {
      errorMessage = "Booking with ID " + passenger.getBookingId() + " does not exist.";
    }

    if (errorMessage == null) {
      passengerRepository.save(passenger);
    }
    return errorMessage;
  }

  /**
   * Get the number of passengers in the application state.
   *
   * @return The total number of passengers stored in the database
   */
  @Operation(summary = "Get the number of passengers",
      description = "Get the total number of passengers stored in the database")
  public long getCount() {
    return passengerRepository.count();
  }
}
