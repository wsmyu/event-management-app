package org.humber.project.services.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.humber.project.domain.Feedback;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.FeedbackValidationException;
import org.humber.project.services.FeedbackValidationService;
import org.humber.project.services.FeedbackJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FeedbackValidation implements FeedbackValidationService {
    private final FeedbackJPAService feedbackJPAService;

    @Autowired
    public FeedbackValidation(FeedbackJPAService feedbackJPAService) {
        this.feedbackJPAService = feedbackJPAService;
    }
    @Override
    public void validateFeedbackSubmission(@NonNull Feedback feedback) {
        List<ErrorCode> errors = new ArrayList<>();

        if (!StringUtils.hasText(String.valueOf(feedback.getRating()))) {
            log.error("Rating is null or empty");
            errors.add(ErrorCode.INVALID_RATING);
        }

        if (!StringUtils.hasText(feedback.getMessage())) {
            log.error("Message is null or empty");
            errors.add(ErrorCode.INVALID_MESSAGE);
        }

        if (!errors.isEmpty()) {
            throw new FeedbackValidationException(errors);
        }
    }
}
