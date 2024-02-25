package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.EventNotFoundException;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.services.BookingService;
import org.humber.project.services.EventJPAService;
import org.humber.project.services.EventService;
import org.humber.project.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class EventServiceImpl implements EventService {
    private final EventJPAService eventJPAService;
    private final VenueService venueService;
    private final BookingService bookingService;


    @Autowired
    public EventServiceImpl(EventJPAService eventJPAService, @Lazy VenueService venueService, BookingService bookingService) {
        this.eventJPAService = eventJPAService;
        this.venueService = venueService;
        this.bookingService = bookingService;
    }
    @Override
    public Event createEvent(Event event) throws Exception {
        try {
            // Validate that the event date is in the future
            LocalDate currentDate = LocalDate.now();
            if (event.getEventDate() != null && event.getEventDate().isBefore(currentDate)) {
                throw new IllegalArgumentException("Event date must be in the future");
            }

            // Create a booking request to check venue availability
            VenueBookingRequest bookingRequest = new VenueBookingRequest();
            bookingRequest.setUserId(event.getUserId());
            bookingRequest.setVenueId(event.getVenueId());
            bookingRequest.setBookingDate(event.getEventDate());
            bookingRequest.setBookingStartTime(event.getEventStartTime());
            bookingRequest.setBookingEndTime(event.getEventEndTime());

            // Check if the venue is available for the specified date and time
            boolean isVenueAvailable = venueService.checkVenueAvailability(bookingRequest);
            if (!isVenueAvailable) {
                throw new VenueNotAvailableException("Venue is not available at the requested date and time");
            }

            // Save the event after confirming venue availability
            Event savedEvent = eventJPAService.saveEvent(event);
            bookingRequest.setEventId(savedEvent.getEventId());

            // Book the venue now that the event is successfully saved
            venueService.bookVenue(bookingRequest);

            return savedEvent;
        } catch (VenueNotAvailableException e) {
            System.err.println("Venue is not available: " + e.getMessage());
            // Handle the exception appropriately, e.g., log it or return an error response
            throw e; // Rethrow the exception to stop further processing
        } catch (Exception e) {
            System.err.println("Failed to create event: " + e.getMessage());
            throw new Exception("Failed to create event", e);
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
    public Event updateEvent(Long eventId, Event event) {
        Event existingEvent = eventJPAService.findEventById(eventId);
        if (existingEvent == null) {
            throw new EventNotFoundException("Event not found with ID: " + eventId);
        }

        //Add validate user later
        existingEvent.setEventName(event.getEventName());
        existingEvent.setEventType(event.getEventType());
        existingEvent.setEventDate(event.getEventDate());
        existingEvent.setEventStartTime(event.getEventStartTime());
        existingEvent.setEventEndTime(event.getEventEndTime());
        existingEvent.setEventDescription(event.getEventDescription());

        return eventJPAService.saveEvent(existingEvent);
    }
    @Override
    public void deleteEvent(Long eventId) {
        //Add validate user later

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
    }
}
