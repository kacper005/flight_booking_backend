package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for the User entity.
 */
@RestController
@RequestMapping("/users")
public class UserController {
  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  /**
   * Get all users.
   *
   * @return List of all users currently stored in the application state
   */
  @GetMapping
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
  public ResponseEntity<User> getUserById(@PathVariable Integer id) {
    ResponseEntity<User> response;
    User user = userService.findByID(id);

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
   * @return Response entity with a message
   */
  @PostMapping()
  public ResponseEntity<String> add(@RequestBody User user) {
    ResponseEntity<String> response;

    try {
      logger.info("Adding user: " + user.getUserId() + " " + user.getEmail() + " " +
          user.getPhone() + " " + user.getFirstName() + " " + user.getLastName() + " " +
          user.getDateOfBirth() + " " + user.getCountry() + " " + user.getGender() + " " +
          user.getRole() + " " + user.getCreatedAt());
      userService.add(user);
      response = new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      response = new ResponseEntity<>("Failed to add user: " + e.getMessage(),
          HttpStatus.BAD_REQUEST);

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

}
