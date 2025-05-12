package edu.ntnu.flightbookingbackend;

import edu.ntnu.flightbookingbackend.config.DataInitializer;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main entrypoint class for the Flight Booking Backend application.
 *
 * <p>This class contains the main method to run the Spring Boot application and a CommandLineRunner
 * bean to initialize the database with test data.
 */
@SpringBootApplication
public class FlightBookingBackendApplication {
  /**
   * Main method to run the Spring Boot application.
   *
   * @param args command line arguments
   */
  @Schema(description = "Main entrypoint class for the application.")
  public static void main(String[] args) {
    SpringApplication.run(FlightBookingBackendApplication.class, args);
  }

  @Bean
  CommandLineRunner initializeDatabase(DataInitializer dataInitializer) {
    return args -> {
      dataInitializer.initializeUsers();
      dataInitializer.initializeAirlines();
      dataInitializer.initializeAirports();
      dataInitializer.initializePrices();
      dataInitializer.initializeFlights();
    };
  }
}
