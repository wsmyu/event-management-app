package org.humber.project.transformers;

import org.humber.project.domain.Feedback;
import org.humber.project.entities.FeedbackEntity;
import org.humber.project.entities.UserEntity;
import org.humber.project.repositories.UserJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedbackEntityTransformer {

    public FeedbackEntity transformToFeedbackEntity(Feedback feedback, UserJPARepository userJPARepository) {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        UserEntity userEntity = userJPARepository.findById(feedback.getUserId()).orElse(null);

        feedbackEntity.setUserEntity(userEntity);
        feedbackEntity.setMessage(feedback.getMessage());
        feedbackEntity.setTimestamp(feedback.getTimestamp());
        feedbackEntity.setRating(feedback.getRating()); // Set rating
        return feedbackEntity;
    }

    public static Feedback transformToFeedback(FeedbackEntity feedbackEntity) {
        return Feedback.builder()
                .feedbackId(feedbackEntity.getFeedbackId())
                .userId(feedbackEntity.getUserEntity().getUserId())
                .message(feedbackEntity.getMessage())
                .timestamp(feedbackEntity.getTimestamp())
                .rating(feedbackEntity.getRating()) // Map rating attribute
                .build();
    }

    public static List<Feedback> transformToFeedbacks(List<FeedbackEntity> entities) {
        return entities.stream()
                .map(FeedbackEntityTransformer::transformToFeedback)
                .collect(Collectors.toList());
    }
}
