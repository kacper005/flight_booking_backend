package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.Passenger;
import edu.ntnu.flightbookingbackend.service.PassengerService;

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
 * REST API controller for the Passenger entity.
 */


@RestController
@RequestMapping("/passengers")
public class PassengerController {

  private static Logger logger = LoggerFactory.getLogger(PassengerController.class);

  @Autowired
  private PassengerService passengerService;

  /**
   * Get all passengers.
   *
   * @return List of all passengers currently stored in the application state
   */
    @GetMapping
    @Operation(
        summary = "Get all passengers",
        description = "Returns a list of all passengers currently stored in the application state"
    )
    public Iterable<Passenger> getAll() {
        return passengerService.getAll();
    }

    /**
     * Get a specific passenger by ID.
     *
     * @param id ID of the passenger to be returned
     * @return Passenger with the given ID or status 404
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get passenger by ID",
        description = "Fetches a passenger based on the provided ID"
    )
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Integer id) {
        ResponseEntity<Passenger> response;
        Passenger passenger = passengerService.findByID(id);

        if (passenger != null) {
            response = new ResponseEntity<>(passenger, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Add a new passenger.
     *
     * @param passenger Passenger to be added
     * @return Passenger that was added
     */
    @PostMapping
    @Operation(
        summary = "Add a new passenger",
        description = "Adds a new passenger to the application state"
    )
    public ResponseEntity<String> add(@RequestBody Passenger passenger) {
        ResponseEntity<String> response;

        try{passengerService.add(passenger);
        logger.info("Passenger added: " + passenger);
        response = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to add passenger: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * Update a passenger in the application state.
     *
     * @param id ID of the passenger to be updated
     * @param passenger Passenger to update
     * @return 200 OK on success, 400 Bad request or 404 Not found on error
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "Update passenger by ID",
        description = "Updates a passenger based on the provided ID"
    )
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Passenger passenger) {
        ResponseEntity<String> response;

        if (passengerService.passengerExists(id)) {
            try {
                passengerService.update(id, passenger);
                response = new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                response = new ResponseEntity<>("Failed to update passenger: " + e.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


  /**
   * Delete a passenger by ID.
   *
   * @param id ID of the passenger to be deleted
   * return 200 OK on success, 400 Bad request or 404 Not found on error
   */
  @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a passenger",
        description = "Delete a passenger with a given ID from the application state"
    )
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (passengerService.passengerExists(id)) {
      if (passengerService.remove(id)) {
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
   * Deletes all passengers from the application state.
   *
   * @return 200 OK on success, 400 Bad request on error
   */
  @DeleteMapping("/all")
    @Operation(
        summary = "Delete all passengers",
        description = "Delete all passengers from the application state"
    )
  public ResponseEntity<String> deleteAll() {
    ResponseEntity<String> response;

    try {
      passengerService.removeAll();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response = new ResponseEntity<>("Failed to remove all passengers: " + e.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

    return response;
  }

}
