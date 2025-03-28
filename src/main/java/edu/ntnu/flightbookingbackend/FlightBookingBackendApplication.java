package edu.ntnu.flightbookingbackend;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlightBookingBackendApplication {
@Schema(description = "Main entrypoint class for the application.")
  public static void main(String[] args) {
    SpringApplication.run(FlightBookingBackendApplication.class, args);
  }
}
