package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.Airline;
import edu.ntnu.flightbookingbackend.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
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
 * REST API controller for the Airline entity.
 */
@RestController
@RequestMapping("/airlines")
public class AirlineController {
  private static Logger logger = LoggerFactory.getLogger(AirlineController.class);

  @Autowired
  private AirlineService airlineService;

  /**
   * Get all airlines.
   *
   * @return List of all airlines currently stored in the application state
   */
  @GetMapping
  @Operation(
      summary = "Get all airlines",
      description = "Returns a list of all airlines currently stored in the application state"
  )
  public Iterable<Airline> getAll() {
    return airlineService.getAll();
  }

  /**
   * Get a specific airline by ID.
   *
   * @param id ID of the airline to be returned
   * @return Airline with the given ID or status 404
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Get airline by ID",
      description = "Fetches an airline based on the provided ID"
  )
  public ResponseEntity<Airline> getAirlineById(@PathVariable Integer id) {
    ResponseEntity<Airline> response;
    Airline airline = airlineService.findByID(id);

    if (airline != null) {
      response = new ResponseEntity<>(airline, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Add a new airline to the application state.
   *
   * @param airline Airline to add
   * @return Status code 201 if successful, 400 if failed
   */
  @PostMapping()
  @Operation(
      summary = "Add a new airline",
      description = "Add a new airline to the application state"
  )
  public ResponseEntity<String> add(@RequestBody Airline airline) {
    ResponseEntity<String> response;

    try {
      airlineService.add(airline);
      logger.info("Airline added.");
      response = new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      response = new ResponseEntity<>("Failed to add airline: " + e.getMessage(),
          HttpStatus.BAD_REQUEST);
      logger.error("Failed to add airline: " + airline.getAirlineId() + " " + airline.getName() +
          " " + airline.getCode() + " " + airline.getCountry());
    }
    return response;
  }

  /**
   * Delete an airline from the application state.
   *
   * @param id ID of the airline to delete
   * @return 200 OK on success, 400 Bad request or 404 Not found on error
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete an airline",
      description = "Delete an airline with a given ID from the application state"
  )
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (airlineService.airlineExists(id)) {
      if (airlineService.remove(id)) {
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
   * Delete all airlines from the application state.
   *
   * @return 200 OK on success, 400 Bad request on error
   */
  @DeleteMapping("/all")
  @Operation(
      summary = "Delete all airlines",
      description = "Delete all airlines from the application state"
  )
  public ResponseEntity<String> deleteAll() {
    ResponseEntity<String> response;

    try {
      airlineService.removeAll();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response = new ResponseEntity<>("Failed to remove all airlines: " + e.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Update an airline",
      description = "Update the details of an airline in the application state"
  )
  public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Airline airline) {
    ResponseEntity<String> response;
    String errorMessage = airlineService.update(id, airline);
    if (errorMessage == null) {
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    return response;
  }
}
