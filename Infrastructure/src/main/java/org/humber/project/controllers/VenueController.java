package org.humber.project.controllers;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.Venue;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.exceptions.VenueNotFoundException;
import org.humber.project.services.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<?> bookVenue(@PathVariable("id") Long venueId, @RequestBody VenueBookingRequest bookingRequest) {
        try {
            Booking booking = venueService.bookVenue(bookingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (VenueNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
