package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.model.Price;
import edu.ntnu.flightbookingbackend.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
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


/**
 * REST API controller for the Flight entity.
 */

@RestController
@RequestMapping("/flights")
public class FlightController {

  private static Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    /**
     * Get all flights.
     *
     * @return List of all flights currently stored in the application state
     */
    @GetMapping
    @Operation(
        summary = "Get all flights",
        description = "Returns a list of all flights currently stored in the application state"
    )
    public Iterable<Flight> getAll() {
        return flightService.getAll();
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
        description = "Fetches a flight based on the provided ID"
    )
    public ResponseEntity<Flight> getFlightById(@PathVariable Integer id) {
        ResponseEntity<Flight> response;
        Flight flight = flightService.findByID(id);

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
        description = "Add a new flight to the application state"
    )
    public ResponseEntity<String> add(@RequestBody Flight flight) {
      try {
        flightService.add(flight);
        logger.info("Flight added with ID: " + flight.getFlightId());
        return new ResponseEntity<>(HttpStatus.CREATED);
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
      description = "Attach prices to an existing flight"
  )
  public ResponseEntity<String> addPricesToFlight(@PathVariable Integer id, @RequestBody
  List<Price> prices) {
    try {
      boolean success = flightService.addPricesToFlight(id, prices);
      if (success) {
        logger.info("Prices added to flight ID: " + id);
        return new ResponseEntity<>("Prices added successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Flight not found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      logger.error("Failed to add prices: " + e.getMessage());
      return new ResponseEntity<>("Failed to add prices", HttpStatus.BAD_REQUEST);
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
    @Operation(
        summary = "Update flight",
        description = "Update a flight in the application state"
    )
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
   * Delete a flight by ID.
   *
   * @param id ID of the flight to be deleted
   * return 200 OK on success, 400 Bad request or 404 Not found on error
   */
  @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a flight",
        description = "Delete a flight with a given ID from the application state"
    )
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
        description = "Delete all flights from the application state"
    )
  public ResponseEntity<String> deleteAll() {
    ResponseEntity<String> response;

    try {
      flightService.removeAll();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response = new ResponseEntity<>("Failed to remove all flights: " + e.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

    return response;
  }

}
