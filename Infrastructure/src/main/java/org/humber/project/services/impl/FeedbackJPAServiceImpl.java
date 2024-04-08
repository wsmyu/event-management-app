package org.humber.project.services.impl;

import org.humber.project.domain.Feedback;
import org.humber.project.entities.FeedbackEntity;
import org.humber.project.repositories.FeedbackJPARepository;
import org.humber.project.repositories.UserJPARepository;
import org.humber.project.services.FeedbackJPAService;
import org.humber.project.transformers.FeedbackEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackJPAServiceImpl implements FeedbackJPAService {

    private final FeedbackJPARepository feedbackJPARepository;
    private final UserJPARepository userJPARepository;
    private final FeedbackEntityTransformer feedbackEntityTransformer;

    @Autowired
    public FeedbackJPAServiceImpl(FeedbackJPARepository feedbackJPARepository, UserJPARepository userJPARepository, FeedbackEntityTransformer feedbackEntityTransformer) {
        this.feedbackJPARepository = feedbackJPARepository;
        this.userJPARepository = userJPARepository;
        this.feedbackEntityTransformer = feedbackEntityTransformer;
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        FeedbackEntity feedbackEntity = feedbackEntityTransformer.transformToFeedbackEntity(feedback, userJPARepository); // Pass userJPARepository
        FeedbackEntity savedEntity = feedbackJPARepository.save(feedbackEntity);
        return feedbackEntityTransformer.transformToFeedback(savedEntity);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        List<FeedbackEntity> feedbackEntities = feedbackJPARepository.findAll();
        return feedbackEntityTransformer.transformToFeedbacks(feedbackEntities);
    }
}
