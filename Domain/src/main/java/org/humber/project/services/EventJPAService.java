package org.humber.project.services;

import org.humber.project.domain.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventJPAService {
    Event saveEvent(Event event);
    Event findEventById(Long eventId);
    List<Event> findEventsByUserId(Long userId);
    void deleteEventById(Long eventId);
    List<Event> findEventsByEventName(String eventName);
    List<Event> findEventsByEventType(String eventType);
    List<Event> findEventsByCity(String city);
    List<Event> findEventsByDateRange(LocalDate startDate, LocalDate endDate);
    List <Event> getAllEvents();
}
