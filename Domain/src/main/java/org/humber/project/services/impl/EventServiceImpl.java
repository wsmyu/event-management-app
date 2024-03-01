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


@Service
public class EventServiceImpl implements EventService {
    private final EventJPAService eventJPAService;
    private final VenueService venueService;
    private final BookingService bookingService;
    private final List<EventValidationService> eventValidationService;


    @Autowired
    public EventServiceImpl(EventJPAService eventJPAService, @Lazy VenueService venueService, BookingService bookingService, List<EventValidationService> eventValidationService) {
        this.eventJPAService = eventJPAService;
        this.venueService = venueService;
        this.bookingService = bookingService;
        this.eventValidationService = eventValidationService;
    }

    @Override
    public Event createEvent(Event event) {
        try {
            //Validate event end time is after the event start time
            for (EventValidationService validationService : eventValidationService) {
                // Perform validation using the current validation service
                validationService.validateEvent(event);
            }

            // Validate that the event date is in the future
            LocalDate currentDate = LocalDate.now();
            if (event.getEventDate() != null && event.getEventDate().isBefore(currentDate)) {
                throw new IllegalArgumentException("Event date must be in the future");
            }

            // Create a booking request to check venue availability
            VenueBookingRequest bookingRequest = createBookingRequestFromEvent(event, null);

            // Check if the venue is available for the specified date and time
            if (!isVenueAvailableForEvent(bookingRequest)) {
                throw new VenueNotAvailableException("Venue is not available at the requested date and time");
            }

            // Save the event after confirming venue availability
            Event savedEvent = eventJPAService.saveEvent(event);
            bookingRequest.setEventId(savedEvent.getEventId());

            // Book the venue now that the event is successfully saved
            venueService.bookVenue(bookingRequest);

            return savedEvent;
        } catch (EventValidationException e){
            System.err.println("Event Invalid: " + e.getMessage());
            throw e; // Rethrow the exception to stop further processing
        }catch (Exception e) {
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
    public Event updateEvent(Long eventId, Event event) {
        try {
            Event existingEvent = eventJPAService.findEventById(eventId);
            if (existingEvent == null) {
                throw new EventNotFoundException("Event not found with ID: " + eventId);
            }

            // Check if the venue ID/event date /event time has been changed
            if (!existingEvent.getVenueId().equals(event.getVenueId()) ||
                    !existingEvent.getEventStartTime().equals(event.getEventStartTime()) ||
                    !existingEvent.getEventEndTime().equals(event.getEventEndTime())) {

                Booking exisitngBooking = bookingService.retrieveBookingByEventId(eventId);

                // Create a booking request to check venue availability
                VenueBookingRequest bookingRequest = createBookingRequestFromEvent(event, eventId);

//             Check if the venue is available for the updated event
                if (!isVenueAvailableForEvent(bookingRequest)) {
                    throw new VenueNotAvailableException("Venue is not available at the requested date and time");
                }

                // If venue is available, update the event's venue ID
                existingEvent.setVenueId(event.getVenueId());

                // Perform booking for the updated event and delete the old booking
                bookingService.deleteBookingById(exisitngBooking.getBookingId());
                venueService.bookVenue(bookingRequest);
            }

            // Update other event details
            existingEvent.setEventName(event.getEventName());
            existingEvent.setEventType(event.getEventType());
            existingEvent.setEventDate(event.getEventDate());
            existingEvent.setEventStartTime(event.getEventStartTime());
            existingEvent.setEventEndTime(event.getEventEndTime());
            existingEvent.setEventDescription(event.getEventDescription());

            // Save the updated event
            return eventJPAService.saveEvent(existingEvent);
        } catch (VenueNotAvailableException e) {
            System.err.println("Venue is not available: " + e.getMessage());
            throw e; // Rethrow the exception to stop further processing
        } catch (Exception e) {
            System.err.println("Failed to update event" + e.getMessage());
            throw new RuntimeException("Failed to update event", e);
        }
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

    private boolean isVenueAvailableForEvent(VenueBookingRequest bookingRequest) {
        try {
            return venueService.checkVenueAvailability(bookingRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check venue availability", e);
        }
    }

    private VenueBookingRequest createBookingRequestFromEvent(Event event, Long eventId) {
        VenueBookingRequest bookingRequest = new VenueBookingRequest();
        if (eventId != null) {
            bookingRequest.setEventId(eventId);
        }
        bookingRequest.setUserId(event.getUserId());
        bookingRequest.setVenueId(event.getVenueId());
        bookingRequest.setBookingDate(event.getEventDate());
        bookingRequest.setBookingStartTime(event.getEventStartTime());
        bookingRequest.setBookingEndTime(event.getEventEndTime());
        return bookingRequest;
    }
}
