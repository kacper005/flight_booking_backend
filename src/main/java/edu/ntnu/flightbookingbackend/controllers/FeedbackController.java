package edu.ntnu.flightbookingbackend.controllers;

import edu.ntnu.flightbookingbackend.model.Feedback;
import edu.ntnu.flightbookingbackend.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for the Feedback entity.
 *
 * <p>This controller handles all CRUD operations related to feedback entries in the system.
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {
  private static Logger logger = LoggerFactory.getLogger(FeedbackController.class);

  @Autowired private FeedbackService feedbackService;

  /**
   * Get all feedback.
   *
   * @return List of all feedback in the system
   */
  @GetMapping
  @Operation(summary = "Get all feedback")
  public Iterable<Feedback> getAllFeedback() {
    return feedbackService.getAllFeedback();
  }

  /**
   * Get a specific feedback by ID.
   *
   * @param id ID of the feedback to be returned
   * @return Feedback entry or status 404 if not found
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get feedback by ID")
  public ResponseEntity<Feedback> getFeedbackById(@PathVariable Integer id) {
    ResponseEntity<Feedback> response;
    Feedback feedback = feedbackService.findById(id);

    if (feedback != null) {
      response = new ResponseEntity<>(feedback, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Add new feedback.
   *
   * @param feedback The feedback to be added
   * @return Response entity with a message
   */
  @PostMapping
  @Operation(summary = "Add new feedback")
  public ResponseEntity<String> addFeedback(@RequestBody Feedback feedback) {
    ResponseEntity<String> response;

    try {
      feedbackService.addFeedback(feedback);
      logger.info("Feedback added successfully");
      response = new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      logger.error("Failed to add feedback: {}", e.getMessage());
      response =
          new ResponseEntity<>("Failed to add feedback: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Update an existing feedback entry.
   *
   * @param id ID of the feedback to update
   * @param feedback Updated feedback entry
   * @return Response entity with a message
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update existing feedback")
  public ResponseEntity<String> updateFeedback(
      @PathVariable Integer id, @RequestBody Feedback feedback) {
    ResponseEntity<String> response;
    String errorMessage = feedbackService.updateFeedback(id, feedback);
    if (errorMessage == null) {
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Delete a feedback entry by ID.
   *
   * @param id ID of the feedback to delete
   * @return Response entity with a message
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete feedback by ID")
  public ResponseEntity<String> deleteFeedback(@PathVariable Integer id) {
    ResponseEntity<String> response;

    if (feedbackService.feedbackExists(id)) {
      if (feedbackService.deleteFeedback(id)) {
        response = new ResponseEntity<>(HttpStatus.OK);
      } else {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Delete all feedback entries.
   *
   * @return Response entity with a message
   */
  @DeleteMapping("/all")
  @Operation(summary = "Delete all feedback")
  public ResponseEntity<String> deleteAllFeedback() {
    ResponseEntity<String> response;
    try {
      feedbackService.deleteAllFeedback();
      response = new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      response =
          new ResponseEntity<>(
              "Failed to remove all feedback: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Get the total number of feedback entries.
   *
   * @return The total number of feedback entries
   */
  @GetMapping("/count")
  @Operation(summary = "Get feedback count")
  public long getFeedbackCount() {
    return feedbackService.getFeedbackCount();
  }

  /**
   * Get all feedback submitted by a specific user.
   *
   * @param userId ID of the user whose feedback is to be retrieved
   * @return List of feedback or 404 if none found
   */
  @GetMapping("/user/{userId}")
  @Operation(
      summary = "Get feedback by user ID",
      description = "Retrieve all feedback submitted by a specific user.")
  public ResponseEntity<List<Feedback>> getFeedbackByUserId(@PathVariable Integer userId) {
    ResponseEntity<List<Feedback>> response;
    List<Feedback> feedbackList = feedbackService.findByUserId(userId);

    if (feedbackList.isEmpty()) {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      response = new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }
    return response;
  }
}
