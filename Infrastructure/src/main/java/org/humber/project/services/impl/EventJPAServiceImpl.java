package org.humber.project.services.impl;

import org.humber.project.domain.Event;
import org.humber.project.entities.EventEntity;
import org.humber.project.repositories.EventJPARepository;
import org.humber.project.services.EventJPAService;
import org.humber.project.transformers.EventEntityTransformer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventJPAServiceImpl implements EventJPAService {
    private final EventJPARepository eventJPARepository;

    public EventJPAServiceImpl(EventJPARepository eventJPARepository) {
        this.eventJPARepository = eventJPARepository;
    }

    @Override
    public Event saveEvent(Event event) {
        EventEntity eventEntity = EventEntityTransformer.transformToEventEntity(event);
        EventEntity savedEventEntity = eventJPARepository.save(eventEntity);
        return EventEntityTransformer.transformToEvent(savedEventEntity);
    }

    @Override
    public Event findEventById(Long eventId) {
        return eventJPARepository.findById(eventId)
                .map(EventEntityTransformer::transformToEvent)
                .orElse(null);
    }

    @Override
    public List<Event> findEventsByUserId(Long userId) {
        return null;
    }

    @Override
    public void deleteEventById(Long eventId) {
        eventJPARepository.deleteById(eventId);
    }

    @Override
    public List<Event> findEventsByEventName(String eventName) {
        return Optional.of(eventJPARepository.findByEventNameContaining(eventName))
                .map(EventEntityTransformer::transformToEvents)
                .orElse(null);
    }

    @Override
    public List<Event> findEventsByEventType(String eventType) {
        return Optional.of(eventJPARepository.findByEventType(eventType))
                .map(EventEntityTransformer::transformToEvents)
                .orElse(null);
    }

    @Override
    public List<Event> findEventsByCity(String city) {
        return Optional.of(eventJPARepository.findByCity(city))
                .map(EventEntityTransformer::transformToEvents)
                .orElse(null);
    }

    @Override
    public List<Event> findEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        return Optional.of(eventJPARepository.findByEventDateBetween(startDate, endDate))
                .map(EventEntityTransformer::transformToEvents)
                .orElse(null);
    }

    @Override
    public List<Event> getAllEvents() {
        return Optional.of(eventJPARepository.findAll())
                .map(EventEntityTransformer::transformToEvents)
                .orElse(null);
    }
}
