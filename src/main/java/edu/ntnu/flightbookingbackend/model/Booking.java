package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
  private int bookingId;
  @Schema(description = "The date of the booking")
  private String bookingDate;
  @Schema(description = "The total price of the booking")
  private float totalPrice;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  @OneToMany(mappedBy = "booking")
  private List<Flight> flights = new ArrayList<>();
  @OneToMany(mappedBy = "booking")
  private List<Passenger> passengers = new ArrayList<>();

  public Booking() {
  }

  public int getBookingId() {
    return bookingId;
  }

  public void setBookingId(int bookingId) {
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

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public void setPassengers(List<Passenger> passengers) {
    this.passengers = passengers;
  }
}
