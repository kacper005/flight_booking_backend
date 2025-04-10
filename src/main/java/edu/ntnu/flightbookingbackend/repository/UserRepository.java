package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for accessing User data in the database.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
