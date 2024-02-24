package org.humber.project.services;

import org.humber.project.domain.Event;

import java.util.List;

public interface EventJPAService {
    Event saveEvent(Event event);
    Event findEventById(Long eventId);
    List<Event> findEventsByUserId(Long userId);
    void deleteEventById(Long eventId);
}
