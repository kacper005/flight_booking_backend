package edu.ntnu.flightbookingbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.flightbookingbackend.enums.FlightStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Represents a flight entity in the flight booking system. */
@Entity
@Schema(
    description =
        "Represents a flight with details such as airline, departure, arrival, and status.")
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "flight_id")
  @Schema(description = "Unique identifier for the flight.", example = "1")
  private Integer flightId;

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

  @Schema(description = "Whether the flight is a round trip or not.", example = "true")
  private boolean roundTrip;

  @Schema(
      description = "List of extra features available on the flight.",
      example = "WiFi, In-seat Power, Extra Legroom")
  private String extraFeatures;

  @Schema(
      description = "List of available classes on the flight",
      example = "Economy, Business, First Class")
  private String availableClasses;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Current status of the flight.", example = "Scheduled, Delayed, Cancelled")
  private FlightStatus status;

  @ManyToMany(mappedBy = "flights")
  @JsonIgnore
  private List<Booking> bookings = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "flight_price",
      joinColumns = @JoinColumn(name = "flight_id"),
      inverseJoinColumns = @JoinColumn(name = "price_id"))
  private List<Price> prices = new ArrayList<>();

  /** Default constructor. */
  public Flight() {}

  public Integer getFlightId() {
    return flightId;
  }

  public void setFlightId(Integer flightId) {
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

  public boolean getRoundTrip() {
    return roundTrip;
  }

  public void setRoundTrip(boolean roundTrip) {
    this.roundTrip = roundTrip;
  }

  public String getExtraFeatures() {
    return extraFeatures;
  }

  public void setExtraFeatures(String extraFeatures) {
    this.extraFeatures = extraFeatures;
  }

  public String getAvailableClasses() {
    return availableClasses;
  }

  public void setAvailableClasses(String availableClasses) {
    this.availableClasses = availableClasses;
  }

  public FlightStatus getStatus() {
    return status;
  }

  public void setStatus(FlightStatus status) {
    this.status = status;
  }

  public List<Booking> getBookings() {
    return bookings;
  }

  public void setBookings(List<Booking> booking) {
    this.bookings = booking;
  }

  /**
   * Adds a booking to the flight and sets the flight in the booking.
   *
   * @param booking The booking to be added.
   */
  public void addBooking(Booking booking) {
    this.bookings.add(booking);
    booking.getFlights().add(this);
  }

  /**
   * Removes a booking from the flight and sets the flight in the booking to null.
   *
   * @param booking The booking to be removed.
   */
  public void removeBooking(Booking booking) {
    this.bookings.remove(booking);
    booking.getFlights().remove(this);
  }

  public List<Price> getPrices() {
    return prices;
  }

  public void setPrices(List<Price> priceList) {
    this.prices = priceList;
  }

  /**
   * Adds a price to the flight and sets the flight in the price.
   *
   * @param price The price to be added.
   */
  public void addPrice(Price price) {
    this.prices.add(price);
    price.getFlights().add(this);
  }

  /**
   * Removes a price from the flight and sets the flight in the price to null.
   *
   * @param price The price to be removed.
   */
  public void removePrice(Price price) {
    this.prices.remove(price);
    price.getFlights().remove(this);
  }
}
