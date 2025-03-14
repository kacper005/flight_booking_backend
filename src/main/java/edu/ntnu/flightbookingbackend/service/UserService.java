package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic related to users.
 */
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Get all users from the application state.
   *
   * @return A list of authors, empty list if there are none
   */
  public Iterable<User> getAll() {
    return userRepository.findAll();
  }

  /**
   * Finds auser by ID. Returns the user if found, null otherwise.
   *
   * @param id ID of the user to find
   * @return The user or null if none found by the given ID
   */
  public User findByID(int id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElse(null);
  }

  /**
   * Add a user to the application state (persist in the database).
   *
   * @param user User to persist
   * @return {@code true} when user is added, {@code false} on error
   */
  public boolean add(User user) {
    boolean added = false;
    boolean emailExists = false;

    if (user != null) {
      User existingUser = findByID(user.getUserId());

      // Check if the email already exists in the database
      for (User u : userRepository.findAll()) {
        if (u.getEmail().equals(user.getEmail())) {
          emailExists = true;
        }
      }

      if (existingUser == null && !emailExists) {
        userRepository.save(user);
        added = true;
      }
    }
    return added;
  }

  /**
   * Remove a user from the application state (database).
   *
   * @param userID ID of the user to delete
   * @return {@code true} when user is deleted, {@code false} when user was not found in the
   * database
   */
  public boolean remove(int userID) {
    Optional<User> user = userRepository.findById(userID);
    if (user.isPresent()) {
      userRepository.delete(user.get());
    }
    return user.isPresent();
  }

  /**
   * Update a user in the application state (persist in the database).
   *
   * @param userId ID of the user to update
   * @param user   User data to update
   * @return Null on success, error message on error
   */
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

    // Check if the email already exists in the database for another user
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
  public long getCount() {
    return userRepository.count();
  }
}
