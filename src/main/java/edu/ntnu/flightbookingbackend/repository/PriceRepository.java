package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Price;
import org.springframework.data.repository.CrudRepository;

/** Repository interface for accessing Price data in the database. */
public interface PriceRepository extends CrudRepository<Price, Integer> {
  /**
   * Checks if a price with the given price and provider name exists in the database.
   *
   * @param price The price to check.
   * @param provider The name of the price provider to check.
   * @return {@code true} if a price with the given price and provider name exists, {@code false}
   *     otherwise.
   */
  boolean existsByPriceAndPriceProviderName(float price, String provider);
}
