package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.BookingValidationService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookingDateValidation implements BookingValidationService {
    @Override
    public void validateBooking(VenueBookingRequest venueBookingRequest) {
        // Validate booking date is in the future
        LocalDate currentDate = LocalDate.now();
        if (venueBookingRequest.getBookingDate() != null && venueBookingRequest.getBookingDate().isBefore(currentDate)) {
            throw new EventValidationException(ErrorCode.INVALID_BOOKING_DATE);
        }
    }
}
