package org.humber.project.services;

import org.humber.project.domain.Booking;
import java.util.List;

public interface BookingJPAService {
    List<Booking> getBookingsByVenueId(Long venueId);
    Booking getBookingByEventId(Long eventId);
    Booking saveBooking(Booking booking);
    void deleteById(Long bookingId);


}
