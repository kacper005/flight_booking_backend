package edu.ntnu.flightbookingbackend.security;

import edu.ntnu.flightbookingbackend.model.User;

/**
 * Data that we will send as a response to the user when the authentication is successful.
 */
public class AuthenticationResponse {
  private final String jwt;
  private final User user;  // Include the user object in the response

  public AuthenticationResponse(String jwt, User user) {
    this.jwt = jwt;
    this.user = user;
  }

  public String getJwt() {
    return jwt;
  }

  public User getUser() {
    return user;
  }
}
