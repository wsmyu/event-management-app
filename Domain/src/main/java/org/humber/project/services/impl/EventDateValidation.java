package org.humber.project.services.impl;

import org.humber.project.domain.Event;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.EventValidationService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EventDateValidation implements EventValidationService {
    @Override
    public void validateEvent(Event event) {
        // Validate event date is in the future
        LocalDate currentDate = LocalDate.now();
        if (event.getEventDate() != null && event.getEventDate().isBefore(currentDate)) {
            throw new EventValidationException("Event date must be in the future");
        }
    }
}
