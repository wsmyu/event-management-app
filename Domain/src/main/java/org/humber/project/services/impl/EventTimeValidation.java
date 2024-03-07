package org.humber.project.services.impl;

import org.humber.project.domain.Event;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.EventValidationService;
import org.springframework.stereotype.Component;

@Component
public class EventTimeValidation implements EventValidationService {
    @Override
    public void validateEvent(Event event) {
        //Validate event end time is after the event start time
        if (event.getEventEndTime().isBefore(event.getEventStartTime())) {
            throw new EventValidationException("Event end time must be after the event start time");
        }
    }
}
