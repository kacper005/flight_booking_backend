package edu.ntnu.flightbookingbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

/** A booking of a flight. Entity class. */
@Entity
@Schema(description = "A booking of a flight")
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "booking_id")
  @Schema(description = "The id of the booking", example = "1")
  private Integer bookingId;

  @Schema(description = "The date of the booking", example = "2023-10-01")
  private String bookingDate;

  @Schema(description = "Number of travellers")
  private int numberOfTravellers;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = true)
  private User user;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "booking_flight",
      joinColumns = @JoinColumn(name = "booking_id"),
      inverseJoinColumns = @JoinColumn(name = "flight_id"))
  private List<Flight> flights = new ArrayList<>();

  /** Default constructor. */
  public Booking() {}

  public Integer getBookingId() {
    return bookingId;
  }

  public void setBookingId(Integer bookingId) {
    this.bookingId = bookingId;
  }

  public String getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(String bookingDate) {
    this.bookingDate = bookingDate;
  }

  public int getNumberOfTravellers() {
    return numberOfTravellers;
  }

  public void setNumberOfTravellers(int numberOfTravellers) {
    this.numberOfTravellers = numberOfTravellers;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Flight> getFlights() {
    return flights;
  }

  public void setFlights(List<Flight> flights) {
    this.flights = flights;
  }

  @JsonIgnore
  public Flight getFlight() {
    return flights.isEmpty() ? null : flights.get(0);
  }

  /**
   * Adds a flight to the booking and sets the booking reference in the flight.
   *
   * @param flight The flight to add.
   */
  public void addFlight(Flight flight) {
    this.flights.add(flight);
    flight.getBookings().add(this);
  }

  /**
   * Removes a flight from the booking and sets the booking reference in the flight.
   *
   * @param flight The flight to remove.
   */
  public void removeFlight(Flight flight) {
    this.flights.remove(flight);
    flight.getBookings().remove(this);
  }
}
