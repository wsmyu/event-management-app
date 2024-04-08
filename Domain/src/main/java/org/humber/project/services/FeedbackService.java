package org.humber.project.services;

import org.humber.project.domain.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback saveFeedback(Feedback feedback);
    List<Feedback> getAllFeedback();

}
