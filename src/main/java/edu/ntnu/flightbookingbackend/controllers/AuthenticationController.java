package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.security.AccessUserService;
import edu.ntnu.flightbookingbackend.security.AuthenticationRequest;
import edu.ntnu.flightbookingbackend.security.AuthenticationResponse;
import edu.ntnu.flightbookingbackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AccessUserService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}
