package edu.ntnu.flightbookingbackend.security;

import edu.ntnu.flightbookingbackend.model.User;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom implementation of Spring Security's UserDetails interface for managing user authentication
 * and authorization.
 *
 * <p>This class wraps a User object and provides the necessary methods to retrieve user details,
 * such as username, password, and authorities (roles).
 */
public class AccessUserDetails implements UserDetails {
  private final User user;

  /**
   * Constructs an AccessUserDetails object with the specified User.
   *
   * @param user The User object containing user information.
   */
  public AccessUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    String role = "ROLE_" + user.getRole().name();
    return List.of(new SimpleGrantedAuthority(role));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail(); // Used for login
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Integer getUserId() {
    return user.getUserId();
  }

  public User getUser() {
    return user;
  }
}
