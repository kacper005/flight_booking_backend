package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.Price;
import edu.ntnu.flightbookingbackend.service.PriceService;
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

/** REST API controller for the Price entity. */
@RestController
@RequestMapping("/prices")
public class PriceController {

  private static Logger logger = LoggerFactory.getLogger(PriceController.class);

  @Autowired private PriceService priceService;

  /**
   * Get all prices.
   *
   * @return List of all prices currently stored in the application state
   */
  @GetMapping
  @Operation(
      summary = "Get all prices",
      description = "Returns a list of all prices currently stored in the application state")
  public Iterable<Price> getAll() {
    return priceService.getAll();
  }

  /**
   * Get a specific price by ID.
   *
   * @param id ID of the price to be returned
   * @return Price with the given ID or status 404
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get price by ID", description = "Fetches a price based on the provided ID")
  public ResponseEntity<Price> getPriceById(@PathVariable Integer id) {
    ResponseEntity<Price> response;
    Price price = priceService.findById(id);

    if (price != null) {
      response = new ResponseEntity<>(price, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

  /**
   * Add a new price.
   *
   * @param price Price to be added
   * @return Status 201 if price was added, 400 if price already exists
   */
  @PostMapping
  @Operation(summary = "Add a new price", description = "Add a new price to the application state")
  public ResponseEntity<Price> add(@RequestBody Price price) {
    try {
      Price savedPrice = priceService.add(price);
      logger.info("Price added successfully.");
      return new ResponseEntity<>(savedPrice, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      logger.error("Failed to add price: " + e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Update a price.
   *
   * @param price Price to be updated
   * @return Status 200 if price was updated, 400 if price does not exist
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update a price", description = "Update a price in the application state")
  public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Price price) {
    ResponseEntity<String> response;
    String errorMessage = priceService.update(id, price);

    if (errorMessage == null) {
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    return response;
  }

  /**
   * Delete a price by ID.
   *
   * @param id ID of the price to be deleted return 200 OK on success, 400 Bad request or 404 Not
   *     found on error
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a price",
      description = "Delete a price with a given ID from the application state")
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (priceService.priceExists(id)) {
      if (priceService.remove(id)) {
        response = new ResponseEntity<>(HttpStatus.OK);
      } else {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

  /** Removes all prices from the application state. */
  @DeleteMapping("/all")
  @Operation(
      summary = "Remove all prices",
      description = "Remove all prices from the application state")
  public void removeAll() {
    priceService.removeAll();
  }
}
