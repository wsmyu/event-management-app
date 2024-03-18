package org.humber.project.services.impl;

import org.humber.project.domain.Booking;
import org.humber.project.domain.Event;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.BookingValidationException;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.BookingValidationService;
import org.springframework.stereotype.Component;

@Component
public class BookingTimeValidation implements BookingValidationService {
    @Override
        public void validateBooking(VenueBookingRequest venueBookingRequest) {
        //Validate event end time is after the event start time
        if (venueBookingRequest.getBookingEndTime().isBefore(venueBookingRequest.getBookingStartTime())) {
            throw new BookingValidationException(ErrorCode.INVALID_BOOKING_TIME);
        }
    }
}
