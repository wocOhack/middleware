package com.woc.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FEEDBACK")
@NamedQuery(name = "Feedback.findAll", query = "SELECT f from FEEDBACK f")
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "feedback_owner_id", referencedColumnName = "id")
    private User feedbackOwner;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 500)
    private String comment;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFeedbackOwner() {
        return feedbackOwner;
    }

    public void setFeedbackOwner(User feedbackOwner) {
        this.feedbackOwner = feedbackOwner;
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
