package org.humber.project.controllers;

import org.humber.project.domain.Booking;
import org.humber.project.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getBookingByEventId(@PathVariable Long eventId) {
        Booking booking = bookingService.retrieveBookingByEventId(eventId);
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found with event ID: " + eventId);
        }
        return ResponseEntity.ok().body(booking);

    }
}
