package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A passenger connected to a booking.
 */
@Entity
@Schema(description = "A passenger connected to a booking")
public class Passenger {
  @Id
  @GeneratedValue
  @Schema(description = "The id of the passenger")
  private int passengerId;
  @Schema(description = "The first name of the passenger")
  private String firstName;
  @Schema(description = "The last name of the passenger")
  private String lastName;
  @Schema(description = "The date of birth of the passenger")
  private String dateOfBirth;
  @Schema(description = "The passport number of the passenger")
  private String passportNumber;

  @ManyToOne
  @JoinColumn(name = "bookingId")
  private Booking booking;

  // TODO: Make sure that the passenger is connected to a booking with an existing booking id (frontend or backend logic)

  public Passenger() {
  }

  public int getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(int passengerId) {
    this.passengerId = passengerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }

  public Booking getBooking() {
    return booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }
}
