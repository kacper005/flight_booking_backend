package edu.ntnu.flightbookingbackend.enums;

/**
 * Enum representing different user roles in the application. Each role is associated with a
 * specific string value.
 */
public enum Role {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN");

  private final String role;

  Role(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
