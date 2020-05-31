package com.woc.repository;

import com.woc.entity.Feedback;
import org.springframework.stereotype.Repository;

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
}
