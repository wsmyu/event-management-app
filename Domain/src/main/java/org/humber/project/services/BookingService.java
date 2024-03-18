package org.humber.project.services;

import org.humber.project.domain.Booking;
import org.humber.project.domain.VenueBookingRequest;

import java.util.List;

public interface BookingService {
    List<Booking> retrieveVenueBookings(Long venueId);
    Booking createBooking(VenueBookingRequest bookingRequest);
    Booking retrieveBookingByEventId(Long eventId);
    void deleteBookingById(Long bookingId);


}
