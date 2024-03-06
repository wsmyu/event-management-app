package org.humber.project.services;

import org.humber.project.domain.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event) ;
    Event retrieveEventDetails(Long eventId);
//    Event updateEvent(Long eventId, Event event) ;
    Event updateEventInfo(Long eventId,Event event);
    Event updateEventVenue(Long eventId, Long venueId);
    void deleteEvent(Long eventId);
}
