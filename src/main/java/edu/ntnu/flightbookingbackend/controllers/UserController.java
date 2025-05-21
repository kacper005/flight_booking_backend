package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.enums.Role;
import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.security.AccessUserDetails;
import edu.ntnu.flightbookingbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST API controller for the User entity. */
@RestController
@RequestMapping("/users")
public class UserController {
  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired private UserService userService;

  /**
   * Get all users.
   *
   * @return List of all users currently stored in the application state
   */
  @GetMapping
  @Operation(
      summary = "Get all users",
      description = "Returns a list of all users currently stored in the application state")
  public Iterable<User> getAll() {
    return userService.getAll();
  }

  /**
   * Get a specific user by ID.
   *
   * @param id ID of the user to be returned
   * @return User with the given ID or status 404
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID", description = "Fetches a user based on the provided ID")
  public ResponseEntity<User> getUserById(@PathVariable Integer id) {
    ResponseEntity<User> response;
    User user = userService.findById(id);

    if (user != null) {
      response = new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return response;
  }

  /**
   * Add a new user to the application state.
   *
   * @param user User to add
   * @return Status code 201 if successful, 400 if failed
   */
  @PostMapping()
  @Operation(summary = "Add a new user", description = "Add a new user to the application state")
  public ResponseEntity<String> add(@RequestBody User user) {
    ResponseEntity<String> response;

    try {
      userService.add(user);
      logger.info("User added.");
      response = new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      logger.error(
          "Failed to add user: "
              + " "
              + user.getEmail()
              + " "
              + user.getPhone()
              + " "
              + user.getFirstName()
              + " "
              + user.getLastName()
              + " "
              + user.getDateOfBirth()
              + " "
              + user.getCountry()
              + " "
              + user.getGender()
              + " "
              + user.getRole()
              + " "
              + user.getCreatedAt());
    }
    return response;
  }

  /**
   * Delete a user from the application state.
   *
   * @param id ID of the user to delete
   * @return 200 OK on success, 400 Bad request or 404 Not found on error
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a user",
      description = "Delete a user with a given ID from the application state")
  public ResponseEntity<String> delete(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (userService.userExists(id)) {
      if (userService.remove(id)) {
        response = new ResponseEntity<>(HttpStatus.OK);
      } else {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Deletes all users from the application state.
   *
   * @return 200 OK on success, 400 Bad request on error
   */
  @DeleteMapping("/all")
  @Operation(
      summary = "Delete all users",
      description = "Delete all users from the application state")
  public ResponseEntity<String> deleteAll() {
    ResponseEntity<String> response;
    try {
      userService.removeAll();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response =
          new ResponseEntity<>(
              "Failed to remove all users: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update a user in the application state.
   *
   * @param id ID of the user to update
   * @param user User data to update
   * @return 200 OK on success, 400 Bad request on error
   */
  @PutMapping("/{id}")
  @Operation(
      summary = "Update a user",
      description = "Update the details of a user in the application state")
  public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody User user) {
    ResponseEntity<String> response;
    String errorMessage = userService.update(id, user);
    if (errorMessage == null) {
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Deletes all users except Admin, from the application state, excluding those with the ADMIN
   * role.
   *
   * @return 200 OK on success, 400 Bad request on error
   */
  @DeleteMapping
  @Operation(
      summary = "Delete all users except Admin",
      description =
          "Delete all users from the application state, excluding those with the ADMIN role")
  public ResponseEntity<String> deleteAllExceptAdmin() {
    Iterable<User> users = userService.getAll();
    int deletedCount = 0;

    for (User user : users) {
      // Don't delete users with the ADMIN role
      if (user.getRole() != Role.ADMIN) {
        userService.remove(user.getUserId());
        deletedCount++;
      }
    }
    return ResponseEntity.ok("Deleted " + deletedCount + " users (excluding admins).");
  }

  /**
   * Get the logged-in user's profile.
   *
   * @param authentication Authentication object containing user details
   * @return ResponseEntity with user information or 404 if not found
   */
  @GetMapping("/me")
  @Operation(
      summary = "Get your own user information",
      description = "Fetches the currently authenticated user's information")
  public ResponseEntity<User> getOwnProfile(Authentication authentication) {
    AccessUserDetails userDetails = (AccessUserDetails) authentication.getPrincipal();
    Integer loggedInUserId = userDetails.getUserId();

    User user = userService.findById(loggedInUserId);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  /**
   * Update the logged-in user's profile.
   *
   * @param updatedUser The updated user information
   * @param authentication Authentication object containing user details
   * @return ResponseEntity with status 200 OK or 400 Bad Request
   */
  @PutMapping("/me")
  @Operation(
      summary = "Update your own profile",
      description = "Allows a user to update their own profile information")
  public ResponseEntity<String> updateProfile(
      @RequestBody User updatedUser, Authentication authentication) {
    AccessUserDetails userDetails = (AccessUserDetails) authentication.getPrincipal();
    Integer loggedInUserId = userDetails.getUserId();

    String errorMessage = userService.updateOwnProfile(loggedInUserId, updatedUser);
    if (errorMessage == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
  }
}
