package org.humber.project.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.BookingService;
import org.humber.project.services.EventJPAService;
import org.humber.project.services.EventService;
import org.humber.project.services.EventValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EventServiceImpl implements EventService {
    private final EventJPAService eventJPAService;
    private final BookingService bookingService;
    private final List<EventValidationService> eventValidationService;

    @Autowired
    public EventServiceImpl(EventJPAService eventJPAService, BookingService bookingService, List<EventValidationService> eventValidationService) {
        this.eventJPAService = eventJPAService;
        this.bookingService = bookingService;
        this.eventValidationService = eventValidationService;
    }

    @Override
    public Event createEvent(Event event) {
        try {
            for (EventValidationService validationService : eventValidationService) {
                // Perform validation using the current validation service
                validationService.validateEvent(event);
            }
            // Save the event after confirming venue availability
            Event savedEvent = eventJPAService.saveEvent(event);

            return savedEvent;

        } catch (EventValidationException e) {
            log.error("Event Invalid: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to create event:{} ", e.getMessage());
            throw new RuntimeException("Failed to update event venue: " + e.getMessage());
        }
    }


    @Override
    public Event retrieveEventDetails(Long eventId) {
        Event event = eventJPAService.findEventById(eventId);
        if (event == null) {
            throw new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND);
        }
        return event;
    }

    @Override
    public Event updateEventInfo(Long eventId, Event event) {
        try {
            for (EventValidationService validationService : eventValidationService) {
                // Perform validation using the current validation service
                validationService.validateEvent(event);
            }
            Event existingEvent = eventJPAService.findEventById(eventId);
            if (existingEvent == null) {
                throw new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND);
            }

            // Update event details
            existingEvent.setEventName(event.getEventName());
            existingEvent.setEventType(event.getEventType());
            existingEvent.setEventDate(event.getEventDate());
            existingEvent.setEventStartTime(event.getEventStartTime());
            existingEvent.setEventEndTime(event.getEventEndTime());
            existingEvent.setEventDescription(event.getEventDescription());
            // Save the updated event
            Event updatedEvent = eventJPAService.saveEvent(existingEvent);
            log.info("Event updated successfully: {}", updatedEvent);

            return updatedEvent;

        } catch (EventValidationException e) {
            log.error("Event Invalid: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to create event:{} ", e.getMessage());
            throw new RuntimeException("Failed to update event venue: " + e.getMessage());
        }
    }

    @Override
    public Event updateEventVenue(Long eventId, Long venueId) {
        try {
            Event existingEvent = eventJPAService.findEventById(eventId);
            if (existingEvent == null) {
                throw new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND);
            }

            // Check if the venue ID has been changed
            if (!Objects.equals(existingEvent.getVenueId(), venueId)) {

                // Update the event's venue ID
                existingEvent.setVenueId(venueId);

                // Save the updated event
                return eventJPAService.saveEvent(existingEvent);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to update event venue : {}", e.getMessage());
            throw new RuntimeException("Failed to update event venue: " + e.getMessage());
        }
    }


    @Override
    public void deleteEvent(Long eventId) {
        //Add validate user later
        try {
            Event event = retrieveEventDetails(eventId);
            if (event == null) {
                throw new EventNotFoundException(ErrorCode.EVENT_NOT_FOUND);
            }

            // Delete associated booking
            Booking booking = bookingService.retrieveBookingByEventId(eventId);

            if (booking != null) {
                bookingService.deleteBookingById(booking.getBookingId());
            }

            eventJPAService.deleteEventById(eventId);
        } catch (Exception e) {
            log.error("Error deleting event with ID {}", eventId);
        }
    }

    @Override
    public List<Event> filterEventsByType(String eventType) {
        return eventJPAService.findEventsByEventType(eventType);
    }

    @Override
    public List<Event> filterEventsByCity(String city) {
        return eventJPAService.findEventsByCity(city);
    }

    @Override
    public List<Event> filterEventsByDate(String timeFrame) {
        LocalDate startDate;
        LocalDate endDate;

        switch (timeFrame) {
            case "thisMonth":
                startDate = LocalDate.now().withDayOfMonth(1);
                endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                break;
            case "nextMonth":
                startDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
                endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                break;
            case "thisYear":
                Year currentYear = Year.now();
                startDate = LocalDate.now();
                endDate = currentYear.atDay(currentYear.length());
                break;
            default:
                throw new IllegalArgumentException("Invalid timeframe: " + timeFrame);
        }
        return eventJPAService.findEventsByDateRange(startDate, endDate);
    }
    @Override
    public List<Event> searchEventsWithFilters(String eventName, String city, String eventType, String timeFrame) {
        List<Event> filteredEvents = new ArrayList<>();

        // Start with all events if no filters applied yet
        if (eventName == null) {
            filteredEvents.addAll(eventJPAService.getAllEvents());
        } else {
            // Apply filters based on the previously filtered events
                // Filter by event name
                filteredEvents.addAll(eventJPAService.findEventsByEventName(eventName));

            if (city != null && !city.isEmpty()) {
                // Filter by city
                filteredEvents.retainAll(filterEventsByCity(city));
            }

            if (eventType != null && !eventType.isEmpty()) {
                // Filter by event type
                filteredEvents.retainAll(filterEventsByType(eventType));
            }

            if (timeFrame != null && !timeFrame.isEmpty()) {
                // Filter by date range
                filteredEvents.retainAll(filterEventsByDate(timeFrame));
            }
        }
        return filteredEvents;
    }
    @Override
    public List<Event> findEventsByUserId(Long userId) {
        return eventJPAService.findEventsByUserId(userId);
    }
}
