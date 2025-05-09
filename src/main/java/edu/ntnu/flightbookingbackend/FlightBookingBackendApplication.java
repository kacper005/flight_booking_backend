package edu.ntnu.flightbookingbackend;
import edu.ntnu.flightbookingbackend.config.DataInitializer;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class FlightBookingBackendApplication {
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
        };
    }


}
