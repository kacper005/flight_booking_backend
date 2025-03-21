package edu.ntnu.flightbookingbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A booking of a flight. Entity class.
 */
@Entity
@Schema(description = "A booking of a flight")
public class Booking {
  @Id
  @GeneratedValue
  @Schema(description = "The id of the booking")
  private Integer bookingId;
  @Schema(description = "The date of the booking")
  private String bookingDate;
  @Schema(description = "The total price of the booking")
  private float totalPrice;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = true)
  @JsonIgnore // Prevent infinite loop
  private User user;
  @ManyToMany
  @JoinTable(
      name = "booking_flight",
      joinColumns = @JoinColumn(name = "booking_id"),
      inverseJoinColumns = @JoinColumn(name = "flight_id")
  )
  private List<Flight> flights = new ArrayList<>();
  @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Passenger> passengers = new ArrayList<>();

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

  public float getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(float totalPrice) {
    this.totalPrice = totalPrice;
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

  public void addFlight(Flight flight) {
    this.flights.add(flight);
    flight.getBookings().add(this);
  }

  public void removeFlight(Flight flight) {
    this.flights.remove(flight);
    flight.getBookings().remove(this);
  }

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public void setPassengers(List<Passenger> passengers) {
    this.passengers = passengers;
  }
}
