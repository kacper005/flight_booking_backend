package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.security.AccessUserDetails;
import edu.ntnu.flightbookingbackend.security.AccessUserService;
import edu.ntnu.flightbookingbackend.security.AuthenticationRequest;
import edu.ntnu.flightbookingbackend.security.AuthenticationResponse;
import edu.ntnu.flightbookingbackend.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for authentication.
 *
 * <p>This controller handles user login and provides user information.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private AccessUserService userDetailsService;

  @Autowired private JwtUtil jwtUtil;

  /**
   * Handles user login by authenticating the provided credentials and generating a JWT token.
   *
   * @param request The authentication request containing username and password.
   * @return A response entity containing the generated JWT token or an error message.
   */
  @PostMapping("/login")
  @Operation (
      summary = "User login",
      description =
          "Authenticates the user with the provided credentials and returns a JWT token if successful.")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

    final String jwt = jwtUtil.generateToken((AccessUserDetails) userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  /**
   * Retrieves the currently authenticated user.
   *
   * @return A response entity containing the authenticated user's information or an error status.
   */
  @GetMapping("/me")
  @Operation (
      summary = "Get current user",
      description = "Returns the currently authenticated user's information.")
  public ResponseEntity<User> getUserMe() {

    UserDetails userDetails =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (userDetails instanceof AccessUserDetails accessUserDetails) {
      return ResponseEntity.ok(accessUserDetails.getUser());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
