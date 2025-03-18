package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents an airline company.")
public class Airline {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique identifier for the airline.", example = "1")
  private int airlineId;

  @Schema(description = "Name of the airline.", example = "Norwegian Air Shuttle")
  private String name;

  @Column(unique = true, nullable = false)
  @Schema(description = "Unique airline code (IATA).", example = "DH")
  private String code;

  @Schema(description = "Country where the airline is registered.", example = "Norway")
  private String country;

  @Schema(description = "URL to the airline's logo.", example = "https://example.com/logo.png")
  private String logoUrl;


  public Airline() {
  }

  public int getAirlineId() {
    return airlineId;
  }

  public void setAirlineId(int airlineId) {
    this.airlineId = airlineId;
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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }
}

