package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
  @Schema(description = "The user id of the booking")
  private int userId;
  @Schema(description = "The flight id of the booking")
  // TODO: Handle foreign key flightId
  private int flightId;
  @Schema(description = "The date of the booking")
  private String bookingDate;
  @Schema(description = "The total price of the booking")
  private float totalPrice;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  // TODO: Create relationship between booking and userID and flightID

  public Booking() {
  }

  public int getBookingId() {
    return bookingId;
  }

  public void setBookingId(int bookingId) {
    this.bookingId = bookingId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getFlightId() {
    return flightId;
  }

  public void setFlightId(int flightId) {
    this.flightId = flightId;
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
}
