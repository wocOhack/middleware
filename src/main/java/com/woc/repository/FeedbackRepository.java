package com.woc.repository;

import com.woc.entity.Feedback;

import java.util.List;

public interface FeedbackRepository {

    public List<Feedback> findAll();

    public void submitFeedback(Feedback feedback);

    public List<Feedback> getFeedbacksByTripId(long tripId);
}
