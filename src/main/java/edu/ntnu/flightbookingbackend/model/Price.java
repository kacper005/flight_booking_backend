package edu.ntnu.flightbookingbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/** A price for a flight, with class and currency. */
@Entity
@Schema(description = "A price for a flight, with class and currency")
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "price_id")
  @Schema(description = "The id of the price")
  private Integer priceId;

  @ManyToMany(mappedBy = "prices")
  @JsonIgnore
  private List<Flight> flights = new ArrayList<>();

  @Schema(description = "The type of class for the flight")
  private String classType;

  @Column(nullable = true)
  @Schema(description = "The price of the flight")
  private float price;

  @Schema(description = "The name of the price provider")
  private String priceProviderName;

  @Schema(description = "The currency of the price")
  private String currency;

  /** Default constructor. */
  public Price() {}

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

  /**
   * Add a flight to the price.
   *
   * @param flight The flight to add
   */
  public void addFlight(Flight flight) {
    this.flights.add(flight);
    flight.getPrices().add(this);
  }

  /**
   * Remove a flight from the price.
   *
   * @param flight The flight to remove
   */
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

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public String getPriceProviderName() {
    return priceProviderName;
  }

  public void setPriceProviderName(String priceProviderName) {
    this.priceProviderName = priceProviderName;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
