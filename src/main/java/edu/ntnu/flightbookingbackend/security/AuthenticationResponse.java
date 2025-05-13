package edu.ntnu.flightbookingbackend.security;

import edu.ntnu.flightbookingbackend.model.User;

/** Data that we will send as a response to the user when the authentication is successful. */
public class AuthenticationResponse {
  private final String jwt;

  /**
   * Constructor for creating an AuthenticationResponse object.
   *
   * @param jwt The JSON Web Token (JWT) to be included in the response.
   */
  public AuthenticationResponse(String jwt) {
    this.jwt = jwt;
  }

  public String getJwt() {
    return jwt;
  }
}
