package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Price data in the database.
 */
public interface PriceRepository extends CrudRepository<Price, Integer> {
    boolean existsByPriceAndPriceProviderName(float price, String provider);

}
