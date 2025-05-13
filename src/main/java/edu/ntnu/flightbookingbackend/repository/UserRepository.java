package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/** Repository interface for accessing User data in the database. */
public interface UserRepository extends CrudRepository<User, Integer> {
  /**
   * Checks if a user with the given email exists in the database.
   *
   * @param email The email to check.
   * @return {@code true} if a user with the given email exists, {@code false} otherwise.
   */
  Optional<User> findByEmail(String email);

  /**
   * Checks if a user with the given email exists in the database.
   *
   * @param mail The email to check.
   * @return {@code true} if a user with the given email exists, {@code false} otherwise.
   */
  boolean existsByEmail(String mail);

  /**
   * Checks if a user with the given phone number exists in the database.
   *
   * @param phone The phone number to check.
   * @return {@code true} if a user with the given phone number exists, {@code false} otherwise.
   */
  Optional<User> findByPhone(String phone);
}
