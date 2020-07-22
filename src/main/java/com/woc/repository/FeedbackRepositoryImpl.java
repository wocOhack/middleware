package com.woc.repository;

import com.woc.entity.Feedback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = entityManager.createNamedQuery("Feedback.findAll").getResultList();
        return feedbacks;
    }
    
    // @Override
    // public Feedback addFeedback() {
    // List<Feedback> feedbacks = entityManager.createNamedQuery("Feedback.findAll").getResultList();
    // return feedbacks;
    // }

    @Override
    @Transactional
    public void submitFeedback(Feedback feedback) {
        entityManager.persist(feedback);
        return;
    }

    @Override
    public List<Feedback> getFeedbacksByTripId(long tripId) {
        List<Feedback> feedbacks = entityManager.createNativeQuery("select * from FEEDBACK f where f.trip_id = " + tripId, Feedback.class).getResultList();
        return feedbacks;
    }
}
