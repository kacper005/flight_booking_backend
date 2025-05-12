package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.model.Price;
import edu.ntnu.flightbookingbackend.repository.PriceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic related to prices.
 */
@Service
@Tag(name = "Price Service", description = "Business logic related to prices")
public class PriceService {
  @Autowired
  private PriceRepository priceRepository;

  @Autowired
  private FlightService flightService;

  /**
   * Get all prices from the application state.
   *
   * @return A list of prices, empty list if there are none
   */
  @Operation(summary = "Get all prices", description = "Get all prices from the application state")
  public Iterable<Price> getAll() {
    return priceRepository.findAll();
  }

  /**
   * Finds a price by ID. Returns the price if found, null otherwise.
   *
   * @param id ID of the price to find
   * @return The price or null if none found by the given ID
   */
  public Price findById(int id) {
    Optional<Price> price = priceRepository.findById(id);
    return price.orElse(null);
  }

  /**
   * Add a price to the application state (persist in the database).
   *
   * @param price Price to persist
   * @return {@code true} when price is added, {@code false} on error
   */
  @Operation(summary = "Add a new price", description = "Add a new price to the application state")
  public Price add(Price price) {
    if (price == null) {
      throw new IllegalArgumentException("Price cannot be null");
    }


    if (price.getPriceId() != null && priceRepository.existsById(price.getPriceId())) {
      throw new IllegalArgumentException("Price with given ID already exists");
    }


    boolean flightExists = price.getFlights() != null && price.getFlights().stream()
        .allMatch(flight -> flightService.findById(flight.getFlightId()) != null);

    if (!flightExists) {
      throw new IllegalArgumentException("One or more associated flights do not exist");
    }

    return priceRepository.save(price);
  }


  /**
   * Remove a price from the application state (database).
   *
   * @param priceId ID of the price to delete
   * @return {@code true} when price is deleted, {@code false} when price was not found in the
   */
  @Operation(summary = "Remove a price", description = "Remove a price from the application state")
  public boolean remove(int priceId) {
    Optional<Price> price = priceRepository.findById(priceId);
    if (price.isPresent()) {
      priceRepository.delete(price.get());
    }
    return price.isPresent();
  }

  /**
   * Update a price in the application state (persist in the database).
   *
   * @param priceId ID of the price to update
   * @param price   Price to update
   * @return null on success, error message on error
   */
  @Operation(summary = "Update a price",
      description = "Update the details of a price in the application state")
  public String update(int priceId, Price price) {
    String errorMessage = null;
    Price existingPrice = findById(priceId);
    FlightService flightService = new FlightService();
    if (existingPrice == null) {
      errorMessage = "No price with id " + priceId + " found";
    } else if (price == null) {
      errorMessage = "No price provided";
    } else if (price.getPriceId() != priceId) {
      errorMessage = "Price ID does no match the ID in JSON data (response body)";
    }

    for (Flight f : price.getFlights()) {
      if (flightService.findById(f.getFlightId()) == null) {
        errorMessage = "Flight with ID " + f.getFlightId() + " does not exist.";
      }
    }

    if (errorMessage == null) {
      priceRepository.save(price);
    }
    return errorMessage;
  }

  /**
   * Check if a price exists in the application state.
   *
   * @param priceId ID of the price to check
   * @return {@code true} if the price exists, {@code false} otherwise
   */
  public boolean priceExists(int priceId) {
    return priceRepository.existsById(priceId);
  }

  /**
   * Removes all prices from the application state.
   */
  @Operation(summary = "Remove all prices",
      description = "Remove all prices from the application state")
  public void removeAll() {
    priceRepository.deleteAll();
  }

  /**
   * Get the number of different prices in the database.
   *
   * @return The number of different prices in the database
   */
  @Operation(summary = "Get the number of prices",
      description = "Get the number of different prices in the database")
  public long getCount() {
    return priceRepository.count();
  }
}
