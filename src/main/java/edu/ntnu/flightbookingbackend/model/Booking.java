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

/**
 * A booking of a flight. Entity class.
 */
@Entity
@Schema(description = "A booking of a flight")

public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "booking_id")
  @Schema(description = "The id of the booking")
  private Integer bookingId;
  @Schema(description = "The date of the booking")
  private String bookingDate;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = true)
  private User user;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "booking_flight",
      joinColumns = @JoinColumn(name = "booking_id"),
      inverseJoinColumns = @JoinColumn(name = "flight_id")
  )
  private List<Flight> flights = new ArrayList<>();

  public Booking() {
  }

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

  public void addFlight(Flight flight) {
    this.flights.add(flight);
    flight.getBookings().add(this);
  }

  public void removeFlight(Flight flight) {
    this.flights.remove(flight);
    flight.getBookings().remove(this);
  }
}
