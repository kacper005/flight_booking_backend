package edu.ntnu.flightbookingbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/** Represents feedback given by a user regarding the website. */
@Entity
@Schema(description = "Represents feedback from a user regarding the website")
public class Feedback {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  @Schema(description = "The unique ID of the feedback", example = "1")
  private Integer feedbackId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @Schema(description = "The ID of the user who submitted the feedback")
  private User user;

  @Schema(description = "Rating given to the website between 1 and 5", example = "4")
  private int rating;

  @Column(columnDefinition = "TEXT")
  @Schema(
      description = "Optional comment regarding the website experience",
      example = "Great service!")
  private String comment;

  @Schema(
      description = "The timestamp when the feedback was submitted",
      example = "2023-10-01T12:00:00Z")
  @Column(updatable = false)
  private String createdAt;

  /**
   * Basic get and set methods for the Feedback class.
   */
  public Feedback() {}

  // Getters and setters
  public Integer getFeedbackId() {
    return feedbackId;
  }

  public void setFeedbackId(Integer feedbackId) {
    this.feedbackId = feedbackId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
}
