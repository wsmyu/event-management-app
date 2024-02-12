package org.humber.project.services.impl;

import org.humber.project.domain.Event;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.services.EventJPAService;
import org.humber.project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventServiceImpl implements EventService {
    private final EventJPAService eventJPAService;

    @Autowired
    public EventServiceImpl(EventJPAService eventJPAService) {
        this.eventJPAService = eventJPAService;
    }

    @Override
    public Event createEvent(Event event) {
        if (event.getEventName() == null || event.getEventName().trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        if (event.getEventType() == null || event.getEventType().trim().isEmpty()) {
            throw new IllegalArgumentException("Event type cannot be empty");
        }

        // Validate that the event date is in the future
        LocalDate currentDate = LocalDate.now();
        if (event.getEventDate() != null && event.getEventDate().isBefore(currentDate)) {
            throw new IllegalArgumentException("Event date must be in the future");
        }
        return eventJPAService.saveEvent(event);
    }

    @Override
    public Event retrieveEventDetails(Long eventId) {
        Event event = eventJPAService.findEventById(eventId);
        if (event == null) {
            throw new EventNotFoundException("Event with ID " + eventId + " not found");
        }
        return event;
    }

    @Override
    public Event updateEvent(Long eventId, Event event) {
        Event existingEvent = eventJPAService.findEventById(eventId);
        if (existingEvent == null) {
            throw new EventNotFoundException("Event not found with ID: " + eventId);
        }

        //Add validate user later

        existingEvent.setEventName(event.getEventName());
        existingEvent.setEventType(event.getEventType());
        existingEvent.setEventDate(event.getEventDate());
        existingEvent.setEventTime(event.getEventTime());
        existingEvent.setEventDescription(event.getEventDescription());

        return eventJPAService.saveEvent(existingEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = retrieveEventDetails(eventId);
        if (event == null) {
            throw new EventNotFoundException("Event with ID " + eventId + " not found");
        }
        //Add validate user later

        eventJPAService.deleteEvent(eventId);
    }
}
