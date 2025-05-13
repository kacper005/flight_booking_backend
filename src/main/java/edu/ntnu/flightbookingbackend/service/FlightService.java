package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.dto.RoundTripFlightDto;
import edu.ntnu.flightbookingbackend.enums.FlightStatus;
import edu.ntnu.flightbookingbackend.model.Flight;
import edu.ntnu.flightbookingbackend.model.Price;
import edu.ntnu.flightbookingbackend.repository.FlightRepository;
import edu.ntnu.flightbookingbackend.repository.PriceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Business logic related to flights. */
@Service
@Tag(name = "Flight Service", description = "Business logic related to flights")
public class FlightService {
  @Autowired private FlightRepository flightRepository;

  @Autowired private PriceRepository priceRepository;

  /**
   * Get all flights from the application state.
   *
   * @return A list of flights, empty list if there are none
   */
  @Operation(
      summary = "Get all flights",
      description = "Get all flights from the application state")
  public Iterable<Flight> getAll() {
    return flightRepository.findAll();
  }

  /**
   * Get all one-way flights from the application state.
   *
   * @return A list of one-way flights, empty list if there are none
   */
  public List<Flight> getOneWayFlights() {
    List<Flight> allFlights = (List<Flight>) flightRepository.findAll();
    return allFlights.stream()
        .filter(flight -> !flight.getRoundTrip())
        .collect(Collectors.toList());
  }

  /**
   * Get all round-trip flights from the application state.
   *
   * @return A list of round-trip flights, empty list if there are none
   */
  @Operation(
      summary = "Get all round-trip flights",
      description = "Get all round-trip flights from the application state")
  public List<RoundTripFlightDto> getRoundTripFlights() {
    List<Flight> allFlights = (List<Flight>) flightRepository.findAll();
    List<RoundTripFlightDto> roundTrips = new ArrayList<>();

    List<Flight> outboundFlights =
        allFlights.stream().filter(Flight::getRoundTrip).collect(Collectors.toList());

    for (Flight outbound : outboundFlights) {
      Optional<Flight> returnFlightOpt =
          outboundFlights.stream()
              .filter(
                  returnFlight ->
                      !outbound.equals(returnFlight)
                          && outbound.getDepartureAirport().equals(returnFlight.getArrivalAirport())
                          && outbound.getArrivalAirport().equals(returnFlight.getDepartureAirport())
                          && returnFlight.getDepartureTime().isAfter(outbound.getArrivalTime())
                          && outbound.getAirline().equals(returnFlight.getAirline()))
              .findFirst();

      returnFlightOpt.ifPresent(
          returnFlight -> roundTrips.add(new RoundTripFlightDto(outbound, returnFlight)));
    }

    return roundTrips;
  }

  /**
   * Finds a flight by ID. Returns the flight if found, null otherwise.
   *
   * @param id ID of the flight to find
   * @return The flight or null if none found by the given ID
   */
  @Operation(
      summary = "Find flight by ID",
      description = "Fetches a flight based on the provided ID")
  public Flight findById(int id) {
    Optional<Flight> flight = flightRepository.findById(id);
    return flight.orElse(null);
  }

  /**
   * Add a flight to the application state (persist in the database).
   *
   * @param flight Flight to persist
   * @return {@code true} when flight is added, {@code false} on error
   */
  @Operation(
      summary = "Add a new flight",
      description = "Add a new flight to the application state")
  public Flight add(Flight flight) {
    if (flight == null) {
      throw new IllegalArgumentException("Flight cannot be null");
    }

    if (flight.getFlightId() != null && flightRepository.existsById(flight.getFlightId())) {
      throw new IllegalArgumentException("Flight with given ID already exists");
    }

    if (flight.getPrices() == null) {
      flight.setPrices(new ArrayList<>());
    }

    return flightRepository.save(flight);
  }

  /**
   * Add prices to an existing flight.
   *
   * @param flightId ID of the flight
   * @param prices List of prices to add
   * @return {@code true} if prices were added, {@code false} if flight not found
   */
  @Transactional
  public Flight addPricesToFlight(Integer flightId, List<Price> prices) {
    Optional<Flight> optionalFlight = flightRepository.findById(flightId);
    if (optionalFlight.isEmpty()) {
      throw new IllegalArgumentException("No flight found with ID: " + flightId);
    }

    Flight flight = optionalFlight.get();

    for (Price price : prices) {
      price.getFlights().add(flight);
      flight.getPrices().add(price);
    }

    return flightRepository.save(flight);
  }

  /**
   * Update a flight in the application state (update in the database).
   *
   * @param flightId ID of the flight to update
   * @param flight Flight data to update
   * @return Success message or error message on failure
   */
  @Operation(summary = "Update a flight", description = "Update a flight in the application state")
  public String update(int flightId, Flight flight) {
    String errorMessage = null;

    if (flight == null) {
      errorMessage = "Flight cannot be null.";
    }

    Flight existingFlight = findById(flightId);
    if (existingFlight == null) {
      errorMessage = "Flight with ID " + flightId + " not found.";
    }

    flight.setFlightId(flightId);
    flightRepository.save(flight);
    return errorMessage;
  }

  /**
   * Search for flights based on the given parameters.
   *
   * @param from Departure airport code
   * @param to Arrival airport code
   * @param start Start date and time
   * @param end End date and time
   * @param roundTrip Whether to search for round-trip flights
   * @return List of matching flights or round-trip flight DTOs
   */
  public List<?> searchFlights(
      String from, String to, LocalDateTime start, LocalDateTime end, boolean roundTrip) {
    List<Flight> allFlights = (List<Flight>) flightRepository.findAll();

    allFlights = allFlights.stream()
        .filter(f -> f.getStatus() != FlightStatus.CANCELLED)
        .collect(Collectors.toList());

    if (!roundTrip) {
      return allFlights.stream()
          .filter(f -> f.getDepartureAirport().getCode().equalsIgnoreCase(from))
          .filter(f -> f.getArrivalAirport().getCode().equalsIgnoreCase(to))
          .filter(f -> !f.getRoundTrip())
          .filter(
              f ->
                  !f.getDepartureTime().isBefore(start)
                      && f.getDepartureTime().isBefore(start.plusDays(1)))
          .collect(Collectors.toList());
    } else {
      List<Flight> outboundFlights =
          allFlights.stream()
              .filter(f -> f.getRoundTrip())
              .filter(f -> f.getDepartureAirport().getCode().equalsIgnoreCase(from))
              .filter(f -> f.getArrivalAirport().getCode().equalsIgnoreCase(to))
              .filter(
                  f ->
                      !f.getDepartureTime().isBefore(start)
                          && f.getDepartureTime().isBefore(start.plusDays(1)))
              .collect(Collectors.toList());

      List<RoundTripFlightDto> roundTrips = new ArrayList<>();

      for (Flight outbound : outboundFlights) {
        allFlights.stream()
            .filter(returnFlight -> returnFlight.getStatus() != FlightStatus.CANCELLED)
            .filter(
                returnFlight ->
                    returnFlight.getRoundTrip()
                        && returnFlight.getDepartureAirport().getCode().equalsIgnoreCase(to)
                        && returnFlight.getArrivalAirport().getCode().equalsIgnoreCase(from)
                        && !returnFlight.getDepartureTime().isBefore(outbound.getArrivalTime())
                        && (end == null
                            || (!returnFlight.getDepartureTime().isBefore(end)
                                && returnFlight.getDepartureTime().isBefore(end.plusDays(1)))))
            .findFirst()
            .ifPresent(
                returnFlight -> roundTrips.add(new RoundTripFlightDto(outbound, returnFlight)));
      }

      return roundTrips;
    }
  }

  /**
   * Remove a flight from the application state (delete from the database).
   *
   * @param flightId ID of the flight to remove
   * @return {@code true} when flight is removed, {@code false} on error
   */
  @Operation(
      summary = "Remove a flight",
      description = "Remove a flight from the application state")
  public boolean remove(int flightId) {
    boolean removed = false;
    Flight flight = findById(flightId);

    if (flight != null) {
      flightRepository.delete(flight);
      removed = true;
    }

    return removed;
  }

  /** Removes all flights from the application state. */
  @Operation(
      summary = "Remove all flights",
      description = "Remove all flights from the application state")
  public void removeAll() {
    flightRepository.deleteAll();
  }

  /**
   * Check if a flight exists in the application state.
   *
   * @param flightId ID of the flight to check
   * @return {@code true} if the flight exists, {@code false} otherwise
   */
  public boolean flightExists(int flightId) {
    return flightRepository.existsById(flightId);
  }
}
