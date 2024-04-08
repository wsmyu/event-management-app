package org.humber.project.controllers;

import org.humber.project.domain.Feedback;
import org.humber.project.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/submit")
    public ResponseEntity<Feedback> saveFeedback(@RequestBody Feedback feedback) {
        // Call the service to save the feedback
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);

        if (savedFeedback != null) {
            // If the feedback is saved successfully, return a response with the saved feedback
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
        } else {
            // If there's an issue with saving the feedback, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        return ResponseEntity.ok().body(feedbackList);
    }
}
