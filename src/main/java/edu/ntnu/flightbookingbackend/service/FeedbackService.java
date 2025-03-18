package edu.ntnu.flightbookingbackend.service;

import edu.ntnu.flightbookingbackend.model.Feedback;
import edu.ntnu.flightbookingbackend.model.Passenger;
import edu.ntnu.flightbookingbackend.repository.FeedbackRepository;
import edu.ntnu.flightbookingbackend.repository.PassengerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling feedback-related operations.
 */
@Service
@Tag(name = "Feedback Service", description = "Business logic related to user feedback")
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, PassengerRepository passengerRepository) {
        this.feedbackRepository = feedbackRepository;
        this.passengerRepository = passengerRepository;
    }

    /**
     * Retrieves all feedback entries.
     *
     * @return List of feedback.
     */
    @Operation(summary = "Retrieve all feedback", description = "Fetches all user feedback for the website.")
    public List<Feedback> getAllFeedback() {
        return (List<Feedback>) feedbackRepository.findAll();
    }

    /**
     * Retrieves feedback by ID
     *
     * @param feedbackId The ID of the feedback.
     * @return The feedback entity if found, otherwise null.
     */
    @Operation(summary = "Find feedback by ID", description = "Fetches a feedback entry based on the provided ID.")
    public Feedback findById (int feedbackId) {
        return feedbackRepository.findById(feedbackId).orElse(null);
    }

    /**
     * Retrieves feedback by user ID
     *
     * @param userId The ID of the user.
     * @return A list of feedback submitted by the user.
     */
    @Operation(summary = "Find feedback by user ID", description = "Fetches all feedback submitted by a specific user.")
    public List<Feedback> findByUserId(int userId) {
        return feedbackRepository.findByUserId(userId);
    }

    /**
     * Saves a new feedback entry.
     *
     * @param feedback The feedback entity to save.
     * @return The saved feedback entity or an error message if validation fails.
     */
    @Operation(summary = "Submit new feedback", description = "Users can submit feedback about the website.")
    public String saveFeedback (Feedback feedback) {
        if (feedback == null) {
            return "Invalid feedback data.";
        }

        Optional<Passenger> user = passengerRepository.findById(feedback.getUser().getUserId());
        if (user.isEmpty()) {
            return "User not found.";
        }

        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            return "Rating must be between 1 and 5.";
        }

        feedback.setCreatedAt(LocalDateTime.now().toString());
        feedbackRepository.save(feedback);
        return "Feedback submitted successfully."; // Return null ?? if successful
    }

    /**
     * Updates an existing feedback entry.
     *
     * @param feedbackId The ID of the feedback to update.
     * @param updateFeedback The updated feedback data.
     * @return Null on success, error message if validation fails.
     */
    @Operation(summary = "Update feedback", description = "Allows users to update their feedback entry.")
    public String updateFeedback(int feedbackId, Feedback updateFeedback) {
        if (updateFeedback == null) {
            return "No feedback data provided.";
        }

        Feedback existingFeedback = findById(feedbackId);
        if (existingFeedback == null) {
            return "No feedback found with ID: " + feedbackId;
        }

        if (updateFeedback.getFeedbackId() != feedbackId) {
            return "Feedback ID does not match";
        }

        if (updateFeedback.getRating() < 1 || updateFeedback.getRating() > 5) {
            return "Rating must be between 1 and 5.";
        }

        existingFeedback.setRating(updateFeedback.getRating());
        existingFeedback.setComment(updateFeedback.getComment());
        feedbackRepository.save(existingFeedback);

        return null; // No error
    }

    /**
     * Deletes a feedback by ID.
     *
     * @param feedbackId The ID of the feedback to delete.
     * @return {@code true} if deleted successfully, {@code false} if not found.
     */
    @Operation(summary = "Delete feedback", description = "Allows users to delete their feedback entry.")
    public boolean deleteFeedback(int feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isPresent()) {
            feedbackRepository.delete(feedback.get());
            return true;
        }
        return false;
    }

    /**
     * Retrieves the number of feedback entries in the database.
     *
     * @return The total number of feedback entries stored.
     */
    @Operation(summary = "Get feedback count", description = "Returns the total count of feedback entries in the system.")
    public long getFeedbackCount() {
        return feedbackRepository.count();
    }
}
