package edu.ntnu.flightbookingbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.flightbookingbackend.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** A user of the system. Entity class. */
@Entity
@Schema(description = "A user of the system")
public class User {
  public static Object Role;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  @Schema(description = "The id of the user", example = "1")
  private Integer userId;

  @Column(unique = true, nullable = false)
  @Schema(description = "The email of the user", example = "user@mail.com")
  private String email;

  //  @Convert(converter = CryptoConverter.class)
  @Schema(description = "The password of the user", example = "password123")
  private String password;

  @Schema(description = "The phone number of the user", example = "+4712345678")
  private String phone;

  @Schema(description = "The first name of the user", example = "John")
  private String firstName;

  @Schema(description = "The last name of the user", example = "Doe")
  private String lastName;

  @Schema(description = "The date of birth of the user", example = "1990-01-01")
  private String dateOfBirth;

  @Schema(description = "The country of residence of the user", example = "Uganda")
  private String country;

  @Schema(description = "The gender of the user", example = "Male")
  private String gender;

  @Schema(description = "The role of the user", example = "USER")
  @Enumerated(EnumType.STRING)
  private Role role;

  @Schema(description = "The date the user was created", example = "2023-10-01T12:00:00")
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Booking> bookings = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
  @JsonIgnore
  private List<Feedback> feedback = new ArrayList<>();

  /** Default constructor. */
  public User() {}

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public List<Booking> getBookings() {
    return bookings;
  }

  public void setBookings(List<Booking> bookings) {
    this.bookings = bookings;
  }

  public List<Feedback> getFeedback() {
    return feedback;
  }

  public void setFeedback(List<Feedback> feedback) {
    this.feedback = feedback;
  }
}
