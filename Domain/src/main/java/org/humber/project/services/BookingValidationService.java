package org.humber.project.services;

import org.humber.project.domain.Booking;
import org.humber.project.domain.VenueBookingRequest;

public interface BookingValidationService {
    void validateBooking(VenueBookingRequest venueBookingRequest);
}
