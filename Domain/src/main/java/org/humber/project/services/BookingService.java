package org.humber.project.services;

import org.humber.project.domain.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> retrieveVenueBookings(Long venueId);
    Booking createBooking(Booking booking);
    Booking retrieveBookingByEventId(Long eventId);
    void deleteBookingById(Long bookingId);
    Booking updateBooking(Booking booking);

}
