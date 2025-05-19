package edu.ntnu.flightbookingbackend.security;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A class representing the authentication request containing the username and password. This class
 * is used to transfer the authentication data from the client to the server.
 */
@Schema(
    description =
        "A class representing the authentication request containing the username and password.")
public class AuthenticationRequest {
  @Schema(description = "The username of the user", example = "user@mail.com")
  private String username;
  @Schema(description = "The password of the user", example = "password123")
  private String password;

  /** Default constructor for creating an empty AuthenticationRequest object. */
  public AuthenticationRequest() {}

  /**
   * Constructor for creating an AuthenticationRequest object.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   */
  public AuthenticationRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
