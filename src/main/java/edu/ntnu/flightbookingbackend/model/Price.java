package edu.ntnu.flightbookingbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;

/**
 * A price for a flight, with class and currency.
 */
@Entity
@Schema(description = "A price for a flight, with class and currency")
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "price_id")
  @Schema(description = "The id of the price")
  private Integer priceId;

  @ManyToMany(mappedBy = "prices")
  @JsonBackReference
  private List<Flight> flights;

  @Schema(description = "The type of class for the flight")
  private String classType;
  @Schema(description = "The price of the flight")
  private float price;
  @Schema(description = "The currency of the price")
  private String currency;

  public Price() {
  }

  public Integer getPriceId() {
    return priceId;
  }

  public void setPriceId(Integer priceId) {
    this.priceId = priceId;
  }

  public List<Flight> getFlights() {
    return flights;
  }

  public void setFlights(List<Flight> flights) {
    this.flights = flights;
  }

  public void addFlight(Flight flight) {
    this.flights.add(flight);
    flight.getPrices().add(this);
  }

  public void removeFlight(Flight flight) {
    this.flights.remove(flight);
    flight.getPrices().remove(this);
  }

  public String getClassType() {
    return classType;
  }

  public void setClassType(String classType) {
    this.classType = classType;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
