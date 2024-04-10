package org.humber.project.services;

import org.humber.project.domain.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event) ;
    Event retrieveEventDetails(Long eventId);
    Event updateEventInfo(Long eventId,Event event);
    Event updateEventVenue(Long eventId, Long venueId);
    void deleteEvent(Long eventId);
    List<Event> filterEventsByType(String eventType);
    List<Event> filterEventsByCity(String city);
    List<Event> filterEventsByDate(String timeFrame);
    List<Event> searchEventsWithFilters(String eventName, String city, String eventType, String timeFrame);
    List<Event> findEventsByUserId(Long userId);

}
