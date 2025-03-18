package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Feedback;
import org.springframework.data.repository.CrudRepository;


/**
 * Repository interface for accessing Feedback data in the database.
 */
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {
}
