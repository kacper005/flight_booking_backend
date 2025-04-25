package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.enums.Role;
import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Business logic related to users.
 */
@Service
@Tag(name = "User Service", description = "Business logic related to users")
public class UserService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Get all users from the application state.
   *
   * @return A list of authors, empty list if there are none
   */
  @Operation(summary = "Get all users", description = "Get all users from the application state")
  public Iterable<User> getAll() {
    return userRepository.findAll();
  }

  /**
   * Finds a user by ID. Returns the user if found, null otherwise.
   *
   * @param id ID of the user to find
   * @return The user or null if none found by the given ID
   */
  @Operation(summary = "Find user by ID", description = "Fetches a user based on the provided ID")
  public User findByID(Integer id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElse(null);
  }

  /**
   * Add a user to the application state (persist in the database).
   *
   * @param user User to persist
   * @return {@code true} when user is added, {@code false} when user already exists
   */
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Operation(summary = "Add a new user", description = "Add a new user to the application state")
  public boolean add(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    for (User u : userRepository.findAll()) {
      if (u.getEmail().equals(user.getEmail())) {
        throw new IllegalArgumentException("A user with this email already exists: " + user.getEmail());
      }
      if (u.getPhone().equals(user.getPhone())) {
        throw new IllegalArgumentException("A user with this phone number already exists: " + user.getPhone());
      }
    }

    if (user.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
      if (user.getRole() == null) {
      user.setRole(Role.USER);
    }

    if(user.getCreatedAt() == null) {
      user.setCreatedAt(LocalDateTime.now());
    }

    userRepository.save(user);
    return true;
  }


  /**
   * Remove a user from the application state (database).
   *
   * @param userID ID of the user to delete
   * @return {@code true} when user is deleted, {@code false} when user was not found in the
   * database
   */
  @Operation(summary = "Remove a user", description = "Remove a user from the application state")
  public boolean remove(Integer userID) {
    Optional<User> user = userRepository.findById(userID);
    if (user.isPresent()) {
      userRepository.delete(user.get());
    }
    return user.isPresent();
  }

  /**
   * Removes all users from the application state (database).
   */
  @Operation(summary = "Removes all users",
      description = "Removes all users from the application state")
  public void removeAll() {
    userRepository.deleteAll();
  }

  /**
   * Check if a user exists in the database.
   *
   * @param userId ID of the user to check
   * @return {@code true} if the user exists, {@code false} otherwise
   */
  public boolean userExists(Integer userId) {
    return userRepository.existsById(userId);
  }

  /**
   * Update a user in the application state (persist in the database).
   *
   * @param userId ID of the user to update
   * @param user   User data to update
   * @return Null on success, error message on error
   */
  @Operation(summary = "Update a user",
      description = "Update the details of a user in the application state")
  public String update(Integer userId, User user) {
    String errorMessage = null;
    User existingUser = findByID(userId);
    if (existingUser == null) {
      errorMessage = "No user with id " + userId + " found.";
    } else if (user == null) {
      errorMessage = "No user data provided.";
    } else if (user.getUserId() != userId) {
      errorMessage = "User ID does not match the ID in JSON data (response body).";
    }

    if (user != null) {
      for (User u : userRepository.findAll()) {
        if (u.getEmail().equals(user.getEmail()) && u.getUserId() != user.getUserId()) {
          errorMessage = "Email already exists in the database.";
        }
      }
    }

    if (errorMessage == null) {
      userRepository.save(user);
    }
    return errorMessage;
  }

  /**
   * Get the number of users in the database.
   *
   * @return The total number of users stored in the database
   */
  @Operation(summary = "Get user count",
      description = "Get the total number of users in the database")
  public long getCount() {
    return userRepository.count();
  }
}
