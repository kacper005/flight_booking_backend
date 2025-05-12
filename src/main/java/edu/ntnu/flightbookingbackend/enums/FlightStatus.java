package edu.ntnu.flightbookingbackend.enums;

/**
 * Enum representing the status of a flight.
 *
 * <p>This enum is used to indicate the current status of a flight, such as whether it is scheduled,
 * delayed, or cancelled.
 */
public enum FlightStatus {
  SCHEDULED("Scheduled"),
  DELAYED("Delayed"),
  CANCELLED("Cancelled");

  private final String status;

  FlightStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
