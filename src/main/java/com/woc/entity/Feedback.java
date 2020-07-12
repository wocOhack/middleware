package com.woc.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FEEDBACK")
@NamedQuery(name = "Feedback.findAll", query = "SELECT f from Feedback f")
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "trip_id", nullable = false, length = 6)
    private Long tripId;

    @Column(name = "user_id", nullable = false, length = 6)
    private Long userId;

    @Column(name = "feedback_owner_id", nullable = false, length = 6)
    private Long feedbackOwnerId;

    @Column(name = "rating", nullable = false, length = 2)
    private Integer rating;

    @Column(name = "comment", length = 500)
    private String comment;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFeedbackOwnerId() {
        return feedbackOwnerId;
    }

    public void setFeedbackOwnerId(Long feedbackOwnerId) {
        this.feedbackOwnerId = feedbackOwnerId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
