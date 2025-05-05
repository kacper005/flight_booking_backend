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
import java.time.LocalDateTime;

@SpringBootApplication
public class FlightBookingBackendApplication {
@Schema(description = "Main entrypoint class for the application.")
  public static void main(String[] args) {
    SpringApplication.run(FlightBookingBackendApplication.class, args);
  }

  @Bean
  public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      if (!userRepository.existsByEmail("chuck@gmail.com")) {
        User admin = new User();
        admin.setEmail("chuck@gmail.com");
        admin.setPassword(passwordEncoder.encode("Nunchucks2024"));
        admin.setPhone("+4799887766");
        admin.setFirstName("Chuck");
        admin.setLastName("Norris");
        admin.setDateOfBirth("02.02.1992");
        admin.setCountry("USA");
        admin.setGender("Male");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(LocalDateTime.now());
        userRepository.save(admin);
        System.out.println("Admin user Chuck created.");
      }

      if (!userRepository.existsByEmail("dave@gmail.com")) {
        User user = new User();
        user.setEmail("dave@gmail.com");
        user.setPassword(passwordEncoder.encode("Dangerous2024"));
        user.setPhone("+4798765432");
        user.setFirstName("Dave");
        user.setLastName("Norman");
        user.setDateOfBirth("01.01.1991");
        user.setCountry("Norway");
        user.setGender("Male");
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        System.out.println("Regular user Dave created.");
      }
    };
  }


}
