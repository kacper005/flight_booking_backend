package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Represents an airline company.")
public class Airline {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "airline_id")
  @Schema(description = "Unique identifier for the airline.", example = "1")
  private Integer airlineId;

  @Column(nullable = false)
  @Schema(description = "Name of the airline.", example = "Norwegian Air Shuttle")
  private String name;

  @Column(unique = true, nullable = false)
  @Schema(description = "Unique airline code.", example = "DH")
  private String code;

  @Schema(description = "Country where the airline is registered.", example = "Norway")
  private String country;

  @Schema(description = "Filename of the airline's logo.", example = "norwegian.png")
  private String logoFileName;


  public Airline() {
  }

  public Integer getAirlineId() {
    return airlineId;
  }

  public void setAirlineId(Integer airlineId) {
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

  public String getLogoFileName() {
    return logoFileName;
  }

  public void setLogoFileName(String logoFileName) {
    this.logoFileName = logoFileName;
  }
}

