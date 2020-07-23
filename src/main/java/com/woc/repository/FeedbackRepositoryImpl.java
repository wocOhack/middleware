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
    public boolean doesFeedbackAlreadyExist(long tripId, long feedbackOwnerId) {
        List<Feedback> feedbacks = entityManager.createNativeQuery("select * from FEEDBACK f where f.trip_id = " + tripId + " and f.feedback_owner_id=" + feedbackOwnerId, Feedback.class).getResultList();
        if(feedbacks == null || feedbacks.size() < 1) {
            return false;
        }
        return true;
    }

    @Override
    public Long getFeedbackCountForUser(Long userId) {
        List<Feedback> feedbacksForUser = entityManager.createNativeQuery("select * from FEEDBACK f where f.user_id=" + userId, Feedback.class).getResultList();
        if(feedbacksForUser == null || feedbacksForUser.size() < 1) {
            return 0l;
        }
        return Long.valueOf(feedbacksForUser.size());
    }
}
