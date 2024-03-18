package org.humber.project.services.impl;

import org.humber.project.domain.Event;
import org.humber.project.domain.VenueBookingRequest;
import org.humber.project.exceptions.BookingValidationException;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.services.BookingValidationService;
import org.humber.project.services.EventService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BookingDateTimeMatchValidation implements BookingValidationService {
    private final EventService eventService;

    public BookingDateTimeMatchValidation(@Lazy EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void validateBooking(VenueBookingRequest venueBookingRequest) {
        // Retrieve the event details
        Event event = eventService.retrieveEventDetails(venueBookingRequest.getEventId());

        //Check if the booking date and time match with event date and time
        if (!venueBookingRequest.getBookingDate().isEqual(event.getEventDate()) ||
                !venueBookingRequest.getBookingStartTime().equals(event.getEventStartTime())
                || !venueBookingRequest.getBookingEndTime().equals(event.getEventEndTime())) {
            throw new BookingValidationException(ErrorCode.BOOKING_DATE_TIME_MISMATCH);
        }
    }
}
