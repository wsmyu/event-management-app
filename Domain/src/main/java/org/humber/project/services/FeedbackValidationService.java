package org.humber.project.services;

import org.humber.project.domain.Feedback;

public interface FeedbackValidationService {
    void validateFeedbackSubmission(Feedback feedback);
}
