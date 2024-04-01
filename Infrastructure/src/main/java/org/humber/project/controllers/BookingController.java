package org.humber.project.controllers;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.services.BookingService;
import org.humber.project.services.BookingValidationService;
import org.humber.project.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final EventService eventService;
    private final List<BookingValidationService> bookingValidationService;

    public BookingController(BookingService bookingService, EventService eventService, List<BookingValidationService> bookingValidationService) {
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.bookingValidationService = bookingValidationService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getBookingByEventId(@PathVariable Long eventId) {
        Booking booking = bookingService.retrieveBookingByEventId(eventId);
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found with event ID: " + eventId);
        }
        return ResponseEntity.ok().body(booking);
    }

    @PostMapping("/{venueId}")
    public ResponseEntity<?> bookVenue(@PathVariable Long venueId, @RequestBody VenueBookingRequest bookingRequest) {
        try {
            for (BookingValidationService validationService : bookingValidationService) {
                // Perform validation using the current validation service
                validationService.validateBooking(bookingRequest);
            }
            // Retrieve the corresponding event
            Event event = eventService.retrieveEventDetails(bookingRequest.getEventId());

            if (!bookingService.checkVenueAvailability(bookingRequest)) {
                throw new VenueNotAvailableException(ErrorCode.VENUE_NOT_AVAILABLE);
            }

            Booking booking;

            //Check if the event has existing venue booking
            //If there is existing venue booking, update the old booking
            if (event.getVenueId() != null) {
                booking = bookingService.updateBooking(bookingRequest);
            } else {
                //Create a new booking with new booking request
                booking = bookingService.createBooking(bookingRequest);
            }
            // Update the venue id of the event
            eventService.updateEventVenue(event.getEventId(), venueId);

            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (VenueNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
