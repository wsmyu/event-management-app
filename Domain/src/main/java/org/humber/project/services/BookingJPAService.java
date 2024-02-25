package org.humber.project.services;

import org.humber.project.domain.Booking;

import java.util.List;

public interface BookingJPAService {
    List<Booking> getBookingsByVenueId(Long venueId);
    Booking saveBooking(Booking booking);

}
