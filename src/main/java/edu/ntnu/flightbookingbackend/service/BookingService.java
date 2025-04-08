package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Booking;
import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.repository.BookingRepository;
import edu.ntnu.flightbookingbackend.repository.FlightRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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

  @Autowired
  private FlightRepository flightRepository;

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
      if (booking == null) {
        return false;
      }

      if (booking.getBookingId() != null && bookingRepository.existsById(booking.getBookingId())) {
        return false;
      }

      if (booking.getFlights() == null) {
        booking.setFlights(new ArrayList<>());
      }

      bookingRepository.save(booking);
      return true;
    }

  /**
   * Add flight to an existing booking.
   *
   * @param bookingId ID of the booking
   * @param flights List of flights to add
   * @return {@code true} if flights were added, {@code false} if booking not found
   */
  @Transactional
  public boolean addFlightsToBooking(int bookingId, List<Flight> flights) {
    Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

    if (optionalBooking.isPresent()) {
      Booking booking = optionalBooking.get(); // This is now inside a Hibernate session

      for (Flight flight : flights) {
        if (flight.getFlightId() == null) {
          flightRepository.save(flight);
        }
        booking.addFlight(flight);
      }

      bookingRepository.save(booking);
      return true;
    }
    return false;
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
  public String update(int bookingId, Booking booking) {
    if (booking == null) {
      return "No booking data provided.";
    }

    if (booking.getBookingId() != bookingId) {
      return "Booking ID does not match the ID in JSON data (response body).";
    }

    Booking existingBooking = findByID(bookingId);
    if (existingBooking == null) {
      return "No booking with id " + bookingId + " found.";
    }

    if (booking.getBookingDate() != null) {
      existingBooking.setBookingDate(booking.getBookingDate());
    }

    bookingRepository.save(existingBooking);
    return null;
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
   * Get the number of bookings in the database.
   *
   * @return The total number of bookings stored in the database
   */
  @Operation(summary = "Get the number of bookings",
      description = "Get the total number of bookings stored in the database")
  public long getCount() {
    return bookingRepository.count();
  }


    /**
     * Check if a booking exists in the database.
     *
     * @param bookingId ID of the booking to check
     * @return {@code true} if the booking exists, {@code false} otherwise
     */
  public boolean bookingExists(int bookingId) {
    return bookingRepository.existsById(bookingId);
  }

  /**
   * Removes all bookings from the application state (database).
   */
  @Operation(summary = "Removes all bookings",
      description = "Removes all bookings from the application state")
  public void removeAll() {
    bookingRepository.deleteAll();
  }

}


