package edu.ntnu.flightbookingbackend.enums;

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