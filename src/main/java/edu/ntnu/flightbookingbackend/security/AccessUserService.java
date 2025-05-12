package edu.ntnu.flightbookingbackend.security;

import edu.ntnu.flightbookingbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for loading user details from the database.
 *
 * <p>This class implements the UserDetailsService interface and provides methods to load user
 * details by username or user ID.
 */
@Service
public class AccessUserService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .map(AccessUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }

  /**
   * Loads user details by user ID.
   *
   * @param userId The ID of the user to load.
   * @return UserDetails object containing user information.
   * @throws UsernameNotFoundException if the user is not found.
   */
  public UserDetails loadUserByUserId(Integer userId) throws UsernameNotFoundException {
    return userRepository
        .findById(userId)
        .map(AccessUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
  }
}
