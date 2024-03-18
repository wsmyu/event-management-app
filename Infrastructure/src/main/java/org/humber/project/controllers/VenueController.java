package org.humber.project.controllers;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.exceptions.VenueNotFoundException;
import org.humber.project.services.BookingService;
import org.humber.project.services.EventService;
import org.humber.project.services.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService venueService;
    private final EventService eventService;
    private final BookingService bookingService;

    public VenueController(VenueService venueService, EventService eventService, BookingService bookingService) {
        this.venueService = venueService;
        this.eventService = eventService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{venueId}")
    public ResponseEntity<Venue> getVenueById(@PathVariable("venueId") Long venueId) {
        Venue venue = venueService.getVenueById(venueId);
        return ResponseEntity.ok(venue);
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<?> bookVenue(@PathVariable("id") Long venueId, @RequestBody VenueBookingRequest bookingRequest) {
        try {
            // Retrieve the corresponding event
            Event event = eventService.retrieveEventDetails(bookingRequest.getEventId());

            if (!venueService.checkVenueAvailability(bookingRequest)) {
                throw new VenueNotAvailableException(ErrorCode.VENUE_NOT_AVAILABLE);
            }

            //Check if the event has existing venue booking
            //If there is existing venue booking, delete the old booking
            if (event.getVenueId() != null) {
                Booking existingBooking = bookingService.retrieveBookingByEventId(event.getEventId());
                // Delete the old booking
                bookingService.deleteBookingById(existingBooking.getBookingId());
            }

            //Create a new booking with new booking request
            Booking booking = bookingService.createBooking(bookingRequest);

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
