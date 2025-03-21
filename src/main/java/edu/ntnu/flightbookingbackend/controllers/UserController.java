package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for the User entity.
 */
@RestController
@RequestMapping("/users")
public class UserController {
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
  public ResponseEntity<User> getOne(@PathVariable Integer id) {
    ResponseEntity<User> response;
    User user = userService.findByID(id);

    if (user != null) {
      response = ResponseEntity.ok(user);
    } else {
      response = ResponseEntity.notFound().build();
    }

    return response;
  }
}
