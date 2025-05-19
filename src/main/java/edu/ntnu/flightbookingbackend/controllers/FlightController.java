package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.dto.RoundTripFlightDto;
import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.model.Price;
import edu.ntnu.flightbookingbackend.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** REST API controller for the Flight entity. */
@RestController
@RequestMapping("/flights")
public class FlightController {

  private static Logger logger = LoggerFactory.getLogger(FlightController.class);

  @Autowired private FlightService flightService;

  /**
   * Get all flights.
   *
   * @return List of all flights currently stored in the application state
   */
  @GetMapping
  @Operation(
      summary = "Get all flights",
      description = "Returns a list of all flights currently stored in the application state")
  public Iterable<Flight> getAll() {
    return flightService.getAll();
  }

  /**
   * Get all one-way flights.
   *
   * @return List of one-way flights
   */
  @GetMapping("/oneway")
  @Operation(
      summary = "Get one-way flights",
      description = "Returns all flights that are not round trips")
  public ResponseEntity<List<Flight>> getOneWayFlights() {
    List<Flight> oneWayFlights = flightService.getOneWayFlights();
    return new ResponseEntity<>(oneWayFlights, HttpStatus.OK);
  }

  /**
   * Get all round-trip flights.
   *
   * @return List of round-trip flights
   */
  @GetMapping("/roundtrip")
  @Operation(
      summary = "Get all round-trip flights",
      description = "Returns a list of round-trip flights (outbound and return combined)")
  public ResponseEntity<List<RoundTripFlightDto>> getRoundTripFlights() {
    List<RoundTripFlightDto> roundTrips = flightService.getRoundTripFlights();
    return new ResponseEntity<>(roundTrips, HttpStatus.OK);
  }

  /**
   * Get a specific flight by ID.
   *
   * @param id ID of the flight to be returned
   * @return Flight with the given ID or status 404
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Get flight by ID",
      description = "Fetches a flight based on the provided ID")
  public ResponseEntity<Flight> getFlightById(@PathVariable Integer id) {
    ResponseEntity<Flight> response;
    Flight flight = flightService.findById(id);

    if (flight != null) {
      response = new ResponseEntity<>(flight, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

  /**
   * Add a new flight.
   *
   * @param flight Flight to be added
   * @return Status 201 if flight was added, 400 if failed
   */
  @PostMapping()
  @Operation(
      summary = "Add a new flight",
      description = "Add a new flight to the application state")
  public ResponseEntity<Flight> add(@RequestBody Flight flight) {
    try {
      Flight savedFlight = flightService.add(flight);
      logger.info("Flight added with ID: " + flight.getFlightId());
      return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    } catch (Exception e) {
      logger.error("Failed to add flight: " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Add prices to an existing flight.
   *
   * @param id ID of the flight to update
   * @param prices List of prices to add
   * @return Status 200 on success, 400 on error, 404 if flight not found
   */
  @PostMapping("/{id}/prices")
  @Operation(
      summary = "Add prices to a flight",
      description = "Attach prices to an existing flight")
  public ResponseEntity<Flight> addPricesToFlight(
      @PathVariable Integer id, @RequestBody List<Price> prices) {
    try {
      Flight savedPriceToFlight = flightService.addPricesToFlight(id, prices);
      logger.info("Prices added to flight ID: " + id);
      return new ResponseEntity<>(savedPriceToFlight, HttpStatus.OK);
    } catch (IllegalArgumentException ie) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Failed to add prices: " + e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Update a flight by ID.
   *
   * @param id ID of the flight to update
   * @param flight Flight data to update
   * @return 200 OK on success, 400 Bad request or 404 Not found on error
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update flight", description = "Update a flight in the application state")
  public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Flight flight) {
    ResponseEntity<String> response;
    String errorMessage = flightService.update(id, flight);

    if (errorMessage == null) {
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    return response;
  }

  /**
   * Search for flights based on departure and arrival locations, start and end times, and
   * round-trip option.
   *
   * @param from Departure location
   * @param to Arrival location
   * @param start Start time
   * @param end End time (optional)
   * @param roundTrip Whether the flight is a round trip or not
   * @return List of matching flights
   */
  @GetMapping("/search")
  @Operation(
      summary = "Search for flights",
      description =
          "Search for flights based on departure and arrival locations, start and end times,"
              + " and round-trip option")
  public ResponseEntity<?> searchFlights(
      @RequestParam String from,
      @RequestParam String to,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end,
      @RequestParam boolean roundTrip) {
    List<?> results = flightService.searchFlights(from, to, start, end, roundTrip);
    return ResponseEntity.ok(results);
  }

  /**
   * Delete a flight by ID.
   *
   * @param id ID of the flight to be deleted return 200 OK on success, 400 Bad request or 404 Not
   *     found on error
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a flight",
      description = "Delete a flight with a given ID from the application state")
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (flightService.flightExists(id)) {
      if (flightService.remove(id)) {
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
   * Deletes all flights from the application state.
   *
   * @return 200 OK on success, 400 Bad request on error
   */
  @DeleteMapping("/all")
  @Operation(
      summary = "Delete all flights",
      description = "Delete all flights from the application state")
  public ResponseEntity<String> deleteAll() {
    ResponseEntity<String> response;

    try {
      flightService.removeAll();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response =
          new ResponseEntity<>(
              "Failed to remove all flights: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return response;
  }
}
