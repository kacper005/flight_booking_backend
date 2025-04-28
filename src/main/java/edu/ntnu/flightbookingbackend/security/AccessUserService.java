package edu.ntnu.flightbookingbackend.security;

import edu.ntnu.flightbookingbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccessUserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
            .map(AccessUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }

  public UserDetails loadUserByUserId(Integer userId) throws UsernameNotFoundException {
    return userRepository.findById(userId)
            .map(AccessUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
  }
}
