package edu.ntnu.flightbookingbackend;
import edu.ntnu.flightbookingbackend.enums.Role;
import edu.ntnu.flightbookingbackend.model.User;
import edu.ntnu.flightbookingbackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FlightBookingBackendApplication {
@Schema(description = "Main entrypoint class for the application.")
  public static void main(String[] args) {
    SpringApplication.run(FlightBookingBackendApplication.class, args);
  }

  @Bean
  public CommandLineRunner initUsers (UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      if (!userRepository.existsByEmail("admin@example.com")) {
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("adminpass"));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
        System.out.println("Admin user created.");
      }

      if (!userRepository.existsByEmail("user@example.com")) {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("userpass"));
        user.setRole(Role.USER);
        userRepository.save(user);
        System.out.println("Regular user created.");
      }
    };
  }
}
