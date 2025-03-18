package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A price for a flight, with class and currency.
 */
@Entity
@Schema(description = "A price for a flight, with class and currency")
public class Price {
  @Id
  @GeneratedValue
  @Schema(description = "The id of the price")
  private int priceId;

  @ManyToOne
  @JoinColumn(name = "flightId", nullable = false)
  private Flight flight;

  @Schema(description = "The type of class for the flight")
  private String classType;
  @Schema(description = "The price of the flight")
  private float price;
  @Schema(description = "The currency of the price")
  private String currency;

  public Price() {
  }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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
