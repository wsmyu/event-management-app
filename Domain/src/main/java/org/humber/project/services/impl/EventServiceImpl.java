package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


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

            // Validate that the event date is in the future
            //Move the validationService later
            LocalDate currentDate = LocalDate.now();
            if (event.getEventDate() != null && event.getEventDate().isBefore(currentDate)) {
                throw new IllegalArgumentException("Event date must be in the future");
            }

            // Save the event after confirming venue availability
            Event savedEvent = eventJPAService.saveEvent(event);
            return savedEvent;

        } catch (EventValidationException e) {
            System.err.println("Event Invalid: " + e.getMessage());
            throw e; // Rethrow the exception to stop further processing
        } catch (Exception e) {
            System.err.println("Failed to create event: " + e.getMessage());
            return null;
        }
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
    public Event updateEventInfo(Long eventId, Event event) {
        try {
            Event existingEvent = eventJPAService.findEventById(eventId);
            if (existingEvent == null) {
                throw new EventNotFoundException("Event not found with ID: " + eventId);
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
            System.out.println("Event updated successfully: " + updatedEvent);

            return updatedEvent;

        } catch (Exception e) {
            System.err.println("Failed to update event" + e.getMessage());
            throw new RuntimeException("Failed to update event", e);
        }
    }

    @Override
    public Event updateEventVenue(Long eventId, Long venueId) {
        try {
            Event existingEvent = eventJPAService.findEventById(eventId);
            if (existingEvent == null) {
                throw new EventNotFoundException("Event not found with ID: " + eventId);
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
            System.err.println("Failed to update event venue" + e.getMessage());
            throw new RuntimeException("Failed to update event venue", e);
        }
    }


    @Override
    public void deleteEvent(Long eventId) {
        //Add validate user later
        try {
            Event event = retrieveEventDetails(eventId);
            if (event == null) {
                throw new EventNotFoundException("Event with ID " + eventId + " not found");
            }

            // Delete associated booking
            Booking booking = bookingService.retrieveBookingByEventId(eventId);

            if (booking != null) {
                bookingService.deleteBookingById(booking.getBookingId());
            }

            eventJPAService.deleteEventById(eventId);
        } catch (Exception e) {
            System.out.println("Error deleting event with ID " + eventId);
            e.printStackTrace();
        }
    }
}
