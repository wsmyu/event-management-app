package org.humber.project.services.impl;

import org.humber.project.domain.Feedback;
import org.humber.project.services.FeedbackJPAService;
import org.humber.project.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackJPAService FeedbackJPAService;

    @Autowired
    public FeedbackServiceImpl(FeedbackJPAService FeedbackJPAService) {
        this.FeedbackJPAService = FeedbackJPAService;
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return FeedbackJPAService.saveFeedback(feedback);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return FeedbackJPAService.getAllFeedback();
    }
}
