package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.BookingNotFoundException;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class BookingServiceImpl implements BookingService {
    private final BookingJPAService bookingJPAService;
    private final EventService eventService;
    private final List<BookingValidationService> bookingValidationService;

    @Autowired
    public BookingServiceImpl(BookingJPAService bookingJPAService,@Lazy EventService eventService, List<BookingValidationService> bookingValidationService) {
        this.bookingJPAService = bookingJPAService;
        this.eventService = eventService;
        this.bookingValidationService = bookingValidationService;
    }

    @Override
    public List<Booking> retrieveVenueBookings(Long venueId) {
        return bookingJPAService.getBookingsByVenueId(venueId);
    }

    @Override
    public Booking createBooking(VenueBookingRequest bookingRequest) {
        for (BookingValidationService validationService : bookingValidationService) {
            // Perform validation using the current validation service
            validationService.validateBooking(bookingRequest);
        }
        if (!checkVenueAvailability(bookingRequest)) {
            throw new VenueNotAvailableException(ErrorCode.VENUE_NOT_AVAILABLE);
        }
        // Create a new Booking entity with the booking details
        Booking booking = new Booking();
        booking.setVenueId(bookingRequest.getVenueId());
        booking.setUserId(bookingRequest.getUserId());
        booking.setEventId(bookingRequest.getEventId());
        booking.setBookingDate(bookingRequest.getBookingDate());
        booking.setBookingStartTime(bookingRequest.getBookingStartTime());
        booking.setBookingEndTime(bookingRequest.getBookingEndTime());

        return bookingJPAService.saveBooking(booking);
    }

    @Override
    public void deleteBookingById(Long bookingId) {
        bookingJPAService.deleteById(bookingId);
    }

    @Override
    public Booking retrieveBookingByEventId(Long eventId) {
        return bookingJPAService.getBookingByEventId(eventId);
    }

    @Override
    public boolean checkVenueAvailability(VenueBookingRequest venueBookingRequest) {
        // Retrieve existing bookings for the venue on the given date
        List<Booking> bookingsForDate = retrieveVenueBookings(venueBookingRequest.getVenueId()).stream()
                .filter(booking -> booking.getBookingDate().equals(venueBookingRequest.getBookingDate()))
                .collect(Collectors.toList());

        // If there are no bookings for the venue on the given date, it's available for the whole day
        if (bookingsForDate.isEmpty()) {
            return true;
        }

        // Check if there are any conflicting bookings at the specified time
        LocalTime bookingStartTime = venueBookingRequest.getBookingStartTime();
        LocalTime bookingEndTime = venueBookingRequest.getBookingEndTime();

        boolean isAvailable = bookingsForDate.stream().noneMatch(booking -> {
            Event event = eventService.retrieveEventDetails(booking.getEventId());
            LocalTime existingBookingStartTime = booking.getBookingStartTime();
            LocalTime existingBookingEndTime = booking.getBookingEndTime();

            // Exclude the booking from the current event from the conflict check
            if (event.getEventId().equals(venueBookingRequest.getEventId())) {
                return false; // Skip this booking
            }

            return (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime));

        });

        return isAvailable;
    }

    @Override
    public Booking updateBooking(VenueBookingRequest bookingRequest) {
        for (BookingValidationService validationService : bookingValidationService) {
            // Perform validation using the current validation service
            validationService.validateBooking(bookingRequest);
        }
        if (!checkVenueAvailability(bookingRequest)) {
            throw new VenueNotAvailableException(ErrorCode.VENUE_NOT_AVAILABLE);
        }

        // Retrieve the existing booking entity by its ID
        Booking existingBooking = bookingJPAService.getBookingByEventId(bookingRequest.getEventId());

        // Update the properties of the existing booking entity with the new data
        existingBooking.setVenueId(bookingRequest.getVenueId());
        existingBooking.setUserId(bookingRequest.getUserId());
        existingBooking.setEventId(bookingRequest.getEventId());
        existingBooking.setBookingDate(bookingRequest.getBookingDate());
        existingBooking.setBookingStartTime(bookingRequest.getBookingStartTime());
        existingBooking.setBookingEndTime(bookingRequest.getBookingEndTime());

        // Save the updated booking entity back to the database
        return bookingJPAService.saveBooking(existingBooking);
    }



}
