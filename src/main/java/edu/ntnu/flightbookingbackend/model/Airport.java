package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * Represents an airport.
 */

@Entity
@Schema(description = "Represents an airport.")
public class Airport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "airport_id")
  @Schema(description = "Unique identifier for the airport.", example = "1")
  private Integer airportId;

  @Schema(description = "Name of the airport.", example = "Gardermoen")
  private String name;

  @Column(unique = true, nullable = false)
  @Schema(description = "Unique airport code (IATA).", example = "OSL")
  private String code;

  @Schema(description = "City where the airport is located.", example = "Oslo")
  private String city;

  @Schema(description = "Country where the airport is located.", example = "Norway")
  private String country;


  public Airport() {
  }

  public Integer getAirportId() {
    return airportId;
  }

  public void setAirportId(Integer airportId) {
    this.airportId = airportId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}

