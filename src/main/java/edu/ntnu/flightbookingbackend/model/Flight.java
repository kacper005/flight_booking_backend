package edu.ntnu.flightbookingbackend.model;

import edu.ntnu.flightbookingbackend.enums.FlightStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a flight entity in the flight booking system.
 */

@Entity
@Schema(description = "Represents a flight with details such as airline, departure, arrival, and status.")
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique identifier for the flight.", example = "1")
  private int flightId;

  @ManyToOne
  @JoinColumn(name = "airline_id", nullable = false)
  @Schema(description = "The airline operating the flight.")
  private Airline airline;

  @Schema(description = "Flight number assigned by the airline.", example = "AA123")
  private String flightNumber;

  @ManyToOne
  @JoinColumn(name = "departure_airport_id", nullable = false)
  @Schema(description = "Airport from which the flight departs.")
  private Airport departureAirport;

  @ManyToOne
  @JoinColumn(name = "arrival_airport_id", nullable = false)
  @Schema(description = "Airport at which the flight arrives.")
  private Airport arrivalAirport;

  @Schema(description = "Scheduled departure time of the flight.", example = "2025-06-15T14:30:00")
  private LocalDateTime departureTime;

  @Schema(description = "Scheduled arrival time of the flight.", example = "2025-06-15T18:45:00")
  private LocalDateTime arrivalTime;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Current status of the flight.", example = "Scheduled,Delayed,Cancelled")
  private FlightStatus status;

  public Flight() {
  }

  public int getFlightId() {
    return flightId;
  }

  public void setFlightId(int flightId) {
    this.flightId = flightId;
  }

  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public Airport getDepartureAirport() {
    return departureAirport;
  }

  public void setDepartureAirport(Airport departureAirport) {
    this.departureAirport = departureAirport;
  }

  public Airport getArrivalAirport() {
    return arrivalAirport;
  }

  public void setArrivalAirport(Airport arrivalAirport) {
    this.arrivalAirport = arrivalAirport;
  }

  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalDateTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(LocalDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public FlightStatus getStatus() {
    return status;
  }

  public void setStatus(FlightStatus status) {
    this.status = status;
  }

}
