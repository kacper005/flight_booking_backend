package edu.ntnu.flightbookingbackend.repository;

import edu.ntnu.flightbookingbackend.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * Repository interface for accessing Feedback data in the database.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByUserId(int userId);
}
