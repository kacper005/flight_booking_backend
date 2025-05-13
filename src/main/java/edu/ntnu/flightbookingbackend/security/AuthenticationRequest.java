package edu.ntnu.flightbookingbackend.security;

/**
 * A class representing the authentication request containing the username and password. This class
 * is used to transfer the authentication data from the client to the server.
 */
public class AuthenticationRequest {
  private String username;
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
