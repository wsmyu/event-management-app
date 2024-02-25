package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.services.BookingJPAService;
import org.humber.project.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingJPAService bookingJPAService;

    @Autowired
    public BookingServiceImpl(BookingJPAService bookingJPAService) {
        this.bookingJPAService = bookingJPAService;
    }

    @Override
    public List<Booking> retrieveVenueBookings(Long venueId){
        return bookingJPAService.getBookingsByVenueId(venueId);
    }

    @Override
    public Booking createBooking (Booking booking){
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
