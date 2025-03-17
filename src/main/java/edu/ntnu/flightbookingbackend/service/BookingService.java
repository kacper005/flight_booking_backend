package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Booking;
import edu.ntnu.flightbookingbackend.repository.BookingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic related to bookings.
 */
@Service
@Tag(name = "Booking Service", description = "Business logic related to bookings")
public class BookingService {
  @Autowired
  private BookingRepository bookingRepository;

  /**
   * Get all bookings from the application state.
   *
   * @return A list of bookings, empty list if there are none
   */
  @Operation(summary = "Get all bookings",
      description = "Get all bookings from the application state")
  public Iterable<Booking> getAll() {
    return bookingRepository.findAll();
  }

  /**
   * Finds a booking by ID. Returns the booking if found, null otherwise.
   *
   * @param id ID of the booking to find
   * @return The booking or null if none found by the given ID
   */
  @Operation(summary = "Find booking by ID",
      description = "Fetches a booking based on the provided ID")
  public Booking findByID(int id) {
    Optional<Booking> booking = bookingRepository.findById(id);
    return booking.orElse(null);
  }

  /**
   * Add a booking to the application state (persist in the database).
   *
   * @param booking Booking to persist
   * @return {@code true} when booking is added, {@code false} on error
   */
  @Operation(summary = "Add a new booking",
      description = "Add a new booking to the application state")
  public boolean add(Booking booking) {
    boolean added = false;
    boolean userExists = false;
    boolean flightExists = false;

    if (booking != null) {
      Booking existingBooking = findByID(booking.getBookingId());

      // Check if the user already exists in the database
      UserService userService = new UserService();
      if (userService.findByID(booking.getUserId()) != null) {
        userExists = true;
      }

      // Check if the flight already exists in the database
      // TODO: Use FlightService.findByID() method to check if the flight exists
      //  (uncomment the code below when FlightService.findByID() is implemented)
      // FlightService flightService = new FlightService();
      // if (flightService.findByID(booking.getFlightId()) != null) {
      //   flightExists = true;
      // }

      // Add the booking if it does not already exist in the database and the user and flight exist
      if (existingBooking == null && userExists && flightExists) {
        bookingRepository.save(booking);
        added = true;
      }
    }
    return added;
  }

  /**
   * Remove a booking from the application state (database).
   *
   * @param bookingID ID of the booking to delete
   * @return {@code true} when booking is deleted, {@code false} when booking was not found in the
   * database
   */
  @Operation(summary = "Remove a booking",
      description = "Remove a booking from the application state")
  public boolean remove(int bookingID) {
    Optional<Booking> booking = bookingRepository.findById(bookingID);
    if (booking.isPresent()) {
      bookingRepository.delete(booking.get());
    }
    return booking.isPresent();
  }

  /**
   * Update a booking in the application state (persist in the database).
   *
   * @param bookingId ID of the booking to update
   * @param booking Booking data to update
   * @return Null on success, error message on error
   */
  @Operation(summary = "Update a booking",
      description = "Update the details of a booking in the application state")
  public String update(Integer bookingId, Booking booking) {
    String errorMessage = null;
    Booking existingBooking = findByID(bookingId);
    UserService userService = new UserService();
    // FlightService flightService = new FlightService();
    if (existingBooking == null) {
      errorMessage = "No booking with id " + bookingId + " found.";
    } else if (booking == null) {
      errorMessage = "No booking data provided.";
    } else if (booking.getBookingId() != bookingId) {
      errorMessage = "Booking ID does not match the ID in JSON data (response body).";
    } else if (userService.findByID(booking.getUserId()) == null) {
      errorMessage = "User with ID " + booking.getUserId() + " does not exist.";
    }
    // TODO: Check if the flight exists in the database - Use method from FlightService

    if (errorMessage == null) {
      bookingRepository.save(booking);
    }
    return errorMessage;
  }

  /**
   * Get the number of bookings in the database.
   *
   * @return The total number of bookings stored in the database
   */
  @Operation(summary = "Get the number of bookings",
      description = "Get the total number of bookings stored in the database")
  public long getCount() {
    return bookingRepository.count();
  }
}
