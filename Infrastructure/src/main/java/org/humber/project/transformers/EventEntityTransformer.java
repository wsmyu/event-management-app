package org.humber.project.transformers;

import org.humber.project.domain.Event;
import org.humber.project.entities.EventEntity;

public class EventEntityTransformer {
    public static EventEntity transformToEventEntity(Event event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(event.getEventId());
        eventEntity.setVenueId(event.getVenueId());
        eventEntity.setUserId(event.getUserId());
        eventEntity.setEventName(event.getEventName());
        eventEntity.setEventType(event.getEventType());
        eventEntity.setEventDate(event.getEventDate());
        eventEntity.setEventStartTime(event.getEventStartTime());
        eventEntity.setEventEndTime(event.getEventEndTime());
        eventEntity.setEventDescription(event.getEventDescription());
        return eventEntity;
    }

    public static Event transformToEvent(EventEntity eventEntity) {
        return Event.builder()
                .eventId(eventEntity.getEventId())
                .userId(eventEntity.getUserId())
                .venueId(eventEntity.getVenueId())
                .eventName(eventEntity.getEventName())
                .eventType(eventEntity.getEventType())
                .eventDate(eventEntity.getEventDate())
                .eventStartTime(eventEntity.getEventStartTime())
                .eventEndTime(eventEntity.getEventEndTime())
                .eventDescription(eventEntity.getEventDescription())
                .build();
    }
}
