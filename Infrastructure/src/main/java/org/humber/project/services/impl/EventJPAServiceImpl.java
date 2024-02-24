package org.humber.project.services.impl;

import org.humber.project.domain.Event;
import org.humber.project.entities.EventEntity;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.repositories.EventJPARepository;
import org.humber.project.services.EventJPAService;
import org.humber.project.transformers.EventEntityTransformer;
import org.springframework.stereotype.Service;

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
    public List<Event> findEventsByUserId(Long eventId){
        return null;
    }

    @Override
    public void deleteEventById(Long eventId) {
        eventJPARepository.deleteById(eventId);
    }
}
