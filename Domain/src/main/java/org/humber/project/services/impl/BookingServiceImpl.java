package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.VenueNotAvailableException;
import org.humber.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service

public class BookingServiceImpl implements BookingService {
    private final BookingJPAService bookingJPAService;
    private final VenueService venueService;
    private final List<BookingValidationService> bookingValidationService;

    @Autowired
    public BookingServiceImpl(BookingJPAService bookingJPAService, @Lazy VenueService venueService, List<BookingValidationService> bookingValidationService) {
        this.bookingJPAService = bookingJPAService;
        this.venueService = venueService;
        this.bookingValidationService = bookingValidationService;
    }

    @Override
    public List<Booking> retrieveVenueBookings(Long venueId){
        return bookingJPAService.getBookingsByVenueId(venueId);
    }

    @Override
    public Booking createBooking (VenueBookingRequest bookingRequest){
        for (BookingValidationService validationService : bookingValidationService) {
            // Perform validation using the current validation service
            validationService.validateBooking(bookingRequest);
        }
        if (!venueService.checkVenueAvailability(bookingRequest)) {
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
        booking.setBookingCreationDate(LocalDate.now());
        return bookingJPAService.saveBooking(booking);
    }

    @Override
    public void deleteBookingById(Long bookingId) {
        bookingJPAService.deleteById(bookingId);
    }

    @Override
    public Booking retrieveBookingByEventId(Long eventId){
        return bookingJPAService.getBookingByEventId(eventId);
    }

   }
