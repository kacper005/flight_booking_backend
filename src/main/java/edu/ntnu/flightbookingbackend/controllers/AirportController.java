package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.Airport;
import edu.ntnu.flightbookingbackend.service.AirportService;
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

/** REST API controller for the Airport entity. */
@RestController
@RequestMapping("/airports")
public class AirportController {
  private static Logger logger = LoggerFactory.getLogger(AirportController.class);

  @Autowired private AirportService airportService;

  /**
   * Get all airports.
   *
   * @return List of all airports currently stored in the application state
   */
  @GetMapping
  @Operation(
      summary = "Get all airports",
      description = "Returns a list of all airports currently stored in the application state")
  public Iterable<Airport> getAll() {
    return airportService.getAll();
  }

  /**
   * Get a specific airport by ID.
   *
   * @param id ID of the airport to be returned
   * @return Airport with the given ID or status 404
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Get airport by ID",
      description = "Fetches an airport based on the provided ID")
  public ResponseEntity<Airport> getAirportById(@PathVariable Integer id) {
    ResponseEntity<Airport> response;
    Airport airport = airportService.findById(id);

    if (airport != null) {
      response = new ResponseEntity<>(airport, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

  /**
   * Add an airport to the application state.
   *
   * @param airport Airport to add
   * @return 201 Created on success, 400 Bad request on error
   */
  @PostMapping()
  @Operation(
      summary = "Add a new airport",
      description = "Add a new airport to the application state")
  public ResponseEntity<String> add(@RequestBody Airport airport) {
    ResponseEntity<String> response;

    try {
      airportService.add(airport);
      logger.info("Airport added.");
      response = new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      response =
          new ResponseEntity<>("Failed to add airport: " + e.getMessage(), HttpStatus.BAD_REQUEST);
      logger.error(
          "Failed to add airport: "
              + airport.getAirportId()
              + " "
              + airport.getName()
              + " "
              + airport.getCode()
              + " "
              + airport.getCity()
              + " "
              + airport.getCountry());
    }
    return response;
  }

  /**
   * Delete an airport from the application state.
   *
   * @param id ID of the airport to delete
   * @return 200 OK on success, 400 Bad request or 404 Not found on error
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete an airport",
      description = "Delete an airport with a given ID from the application state")
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (airportService.airportExists(id)) {
      if (airportService.remove(id)) {
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
   * Remove all airports from the application state.
   *
   * @return 200 OK on success, 400 Bad request on error
   */
  @DeleteMapping("/all")
  @Operation(
      summary = "Delete all airports",
      description = "Delete all airports from the application state")
  public ResponseEntity<String> deleteAll() {
    ResponseEntity<String> response;
    try {
      airportService.removeAll();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response =
          new ResponseEntity<>(
              "Failed to remove all airports: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an airport in the application state.
   *
   * @param id ID of the airport to update
   * @param airport Airport data to update
   * @return 200 OK on success, 400 Bad request on error
   */
  @PutMapping("/{id}")
  @Operation(
      summary = "Update an airport",
      description = "Update the details of an airport in the application state")
  public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Airport airport) {
    ResponseEntity<String> response;
    String errorMessage = airportService.update(id, airport);
    if (errorMessage == null) {
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    return response;
  }
}
