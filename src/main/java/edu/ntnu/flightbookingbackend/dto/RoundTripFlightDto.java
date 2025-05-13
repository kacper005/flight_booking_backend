package edu.ntnu.flightbookingbackend.dto;

import edu.ntnu.flightbookingbackend.model.Flight;

/**
 * Data Transfer Object (DTO) for representing a round trip flight. This class contains two flights:
 * an outbound flight and a return flight.
 */
public class RoundTripFlightDto {

  private Flight outboundFlight;
  private Flight returnFlight;

  /**
   * Constructor for creating a RoundTripFlightDto object.
   *
   * @param outboundFlight Flight object representing the outbound flight
   * @param returnFlight Flight object representing the return flight
   */
  public RoundTripFlightDto(Flight outboundFlight, Flight returnFlight) {
    this.outboundFlight = outboundFlight;
    this.returnFlight = returnFlight;
  }

  public Flight getOutboundFlight() {
    return outboundFlight;
  }

  public void setOutboundFlight(Flight outboundFlight) {
    this.outboundFlight = outboundFlight;
  }

  public Flight getReturnFlight() {
    return returnFlight;
  }

  public void setReturnFlight(Flight returnFlight) {
    this.returnFlight = returnFlight;
  }
}
