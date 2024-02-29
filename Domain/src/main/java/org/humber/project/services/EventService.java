package org.humber.project.services;

import org.humber.project.domain.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event) ;
    Event retrieveEventDetails(Long eventId);
    Event updateEvent(Long eventId, Event event) ;
    void deleteEvent(Long eventId);
}
