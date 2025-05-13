package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.Booking;
import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST API controller for the Booking entity. */
@RestController
@RequestMapping("/bookings")
public class BookingController {

  private static Logger logger = LoggerFactory.getLogger(BookingController.class);

  @Autowired private BookingService bookingService;

  /**
   * Get all bookings.
   *
   * @return List of all bookings currently stored in the application state
   */
  @GetMapping
  @Operation(
      summary = "Get all bookings",
      description = "Returns a list of all bookings currently stored in the application state")
  public Iterable<Booking> getAll() {
    return bookingService.getAll();
  }

  /**
   * Get a specific booking by ID.
   *
   * @param id ID of the booking to be returned
   * @return Booking with the given ID or status 404
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Get booking by ID",
      description = "Fetches a booking based on the provided ID")
  public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
    ResponseEntity<Booking> response;
    Booking booking = bookingService.findById(id);

    if (booking != null) {
      response = new ResponseEntity<>(booking, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

    /**
     * Get all bookings made by a specific user.
     *
     * @param userId ID of the user
     * @return List of bookings made by the user
     */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Integer userId) {
    List<Booking> bookings = bookingService.getBookingsByUserId(userId);
    return ResponseEntity.ok(bookings);
  }


  /**
   * Add a new booking.
   *
   * @param booking Booking to be added
   * @return Status 201 if booking was added, 400 if failed
   */
  @PostMapping
  @Operation(summary = "Add a new booking", description = "Add a new booking to the application state")
  public ResponseEntity<Map<String, Integer>> add(@RequestBody Booking booking) {
    try {
      bookingService.add(booking);
      logger.info("Booking added with ID: " + booking.getBookingId());
      return new ResponseEntity<>(Map.of("bookingId", booking.getBookingId()), HttpStatus.CREATED);
    } catch (Exception e) {
      logger.error("Failed to add booking: " + e.getMessage());
      return new ResponseEntity<>(Map.of("error", -1), HttpStatus.BAD_REQUEST);
    }
  }




  /** Add flight to an existing booking. */
  @PostMapping("/{id}/flights")
  @Operation(summary = "Add flight to booking", description = "Add a flight to an existing booking")
  public ResponseEntity<String> addFlightsToBooking(
      @PathVariable Integer id, @RequestBody List<Flight> flights) {
    try {
      boolean success = bookingService.addFlightsToBooking(id, flights);
      if (success) {
        logger.info("Flights added to booking ID: " + id);
        return new ResponseEntity<>("Flights added successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      logger.error("Failed to add flights: " + e.getMessage());
      return new ResponseEntity<>("Failed to add flights ", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Update a booking in the application state.
   *
   * @param id ID of the booking to update
   * @param booking Booking data to update
   * @return 200 OK on success, 400 Bad request or 404 Not found on error
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update booking", description = "Update a booking in the application state")
  public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Booking booking) {
    ResponseEntity<String> response;
    String errorMessage = bookingService.update(id, booking);

    if (errorMessage == null) {
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    return response;
  }

  /**
   * Delete a booking by ID.
   *
   * @param id ID of the booking to be deleted return 200 OK on success, 400 Bad request or 404 Not
   *     found on error
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a booking",
      description = "Delete a booking with a given ID from the application state")
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (bookingService.bookingExists(id)) {
      if (bookingService.remove(id)) {
        response = new ResponseEntity<>(HttpStatus.OK);
      } else {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

  /**
   * Deletes all bookings from the application state.
   *
   * @return 200 OK on success, 400 Bad request on error
   */
  @DeleteMapping("/all")
  @Operation(
      summary = "Delete all bookings",
      description = "Delete all bookings from the application state")
  public ResponseEntity<String> deleteAll() {
    ResponseEntity<String> response;

    try {
      bookingService.removeAll();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response =
          new ResponseEntity<>(
              "Failed to remove all bookings: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return response;
  }
}
