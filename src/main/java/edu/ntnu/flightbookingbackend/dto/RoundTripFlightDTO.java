package edu.ntnu.flightbookingbackend.dto;

import edu.ntnu.flightbookingbackend.model.Flight;

public class RoundTripFlightDTO {

  private Flight outboundFlight;
  private Flight returnFlight;

  public RoundTripFlightDTO() {}

  public RoundTripFlightDTO(Flight outboundFlight, Flight returnFlight) {
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
